package com.sygic.travel.sdk.synchronization.services

import android.annotation.SuppressLint
import android.content.SharedPreferences
import com.sygic.travel.sdk.common.api.SygicTravelApiClient
import com.sygic.travel.sdk.synchronization.api.model.ApiChangesResponse
import com.sygic.travel.sdk.trips.api.TripConverter
import com.sygic.travel.sdk.trips.api.model.ApiTripItemResponse
import com.sygic.travel.sdk.trips.api.model.ApiUpdateTripResponse
import com.sygic.travel.sdk.trips.model.Trip
import com.sygic.travel.sdk.trips.services.TripsService
import com.sygic.travel.sdk.utils.DateTimeHelper

class SynchronizationService constructor(
	private val sharedPreferences: SharedPreferences,
	private val apiClient: SygicTravelApiClient,
	private val tripConverter: TripConverter,
	private val tripsService: TripsService
) {
	companion object {
		private const val SINCE_KEY = "sync.since"
	}

	@SuppressLint("ApplySharedPref")
	suspend fun synchronize() {
		val since = sharedPreferences.getLong(SINCE_KEY, 0)
		val changesResponse = apiClient.getChanges(
			DateTimeHelper.timestampToDatetime(since)
		).execute().body()!!

		val changedTripIds = arrayListOf<String>()
		val deletedTripIds = arrayListOf<String>()

		for (change in changesResponse.data?.changes ?: arrayListOf()) {
			when (change.type) {
				ApiChangesResponse.ChangeEntry.TYPE_TRIP -> {
					if (change.change == ApiChangesResponse.ChangeEntry.CHANGE_UPDATED) {
						changedTripIds.add(change.id!!)
					} else if (change.change == ApiChangesResponse.ChangeEntry.CHANGE_DELETED) {
						deletedTripIds.add(change.id!!)
					}
				}
				ApiChangesResponse.ChangeEntry.TYPE_FAVORITE -> {
				} // ignore
				ApiChangesResponse.ChangeEntry.TYPE_SETTINGS -> {
				} // ignore
			}
		}

		val changedTripsResponse = apiClient.getTrips(
			changedTripIds.joinToString("|")
		).execute().body()!!

		for (trip in changedTripsResponse.data?.trips ?: arrayListOf()) {
			syncApiChangedTrip(trip)
		}
		for (deletedTripId in deletedTripIds) {
			syncApiDeletedTrip(deletedTripId)
		}

		syncLocalChangedTrips(changedTripIds, deletedTripIds)

		sharedPreferences.edit()
			.putLong(SINCE_KEY, DateTimeHelper.now())
			.commit()
	}

	fun clearUserData() {
		sharedPreferences.edit().remove(SINCE_KEY).apply()
	}

	private suspend fun syncApiChangedTrip(apiTrip: ApiTripItemResponse) {
		var apiTripData: ApiTripItemResponse? = apiTrip
		var localTrip = tripsService.getTrip(apiTrip.id)

		if (localTrip == null) {
			localTrip = Trip()
			localTrip.id = apiTrip.id
		}

		if (localTrip.isChanged) {
			apiTripData = updateTrip(localTrip)
			if (apiTripData == null) {
				// do nothing and let user choose when he will use the app
				return
			}
		}

		tripConverter.fromApi(localTrip, apiTripData!!)
		tripsService.saveTrip(localTrip)
	}

	private suspend fun showDialogAndGetResponse(conflictInfo: ApiUpdateTripResponse.ConflictInfo): Boolean? {
		return false // todo
	}

	private suspend fun syncApiDeletedTrip(deletedTripId: String) {
		tripsService.deleteTrip(deletedTripId)
	}

	private suspend fun syncLocalChangedTrips(apiChangedTripIds: List<String>, apiDeletedTripIds: List<String>) {
		val changedTrips = tripsService.findAllChangedExceptApiChanged(apiChangedTripIds)
		for (changedTrip in changedTrips) {
			syncLocalChangedTrip(changedTrip, apiDeletedTripIds.contains(changedTrip.id))
		}
	}

	private suspend fun syncLocalChangedTrip(trip: Trip, deletedOnApi: Boolean) {
		if (trip.isLocal) {
			val createResponse = apiClient.createTrip(
				tripConverter.toApi(trip)
			).execute().body()!!
			tripsService.replaceTripId(trip, createResponse.data!!.trip.id)
			tripConverter.fromApi(trip, createResponse.data.trip)
			tripsService.saveTrip(trip)

		} else if (!deletedOnApi) {
			val apiTripData = updateTrip(trip)
			tripConverter.fromApi(trip, apiTripData!!)
			tripsService.saveTrip(trip)

		} else {
			tripsService.deleteTrip(trip.id)
		}
	}

	private suspend fun updateTrip(localTrip: Trip): ApiTripItemResponse? {
		val updateResponse = apiClient.updateTrip(
			localTrip.id,
			tripConverter.toApi(localTrip)
		).execute().body()!!

		when (updateResponse.data?.conflictResolution) {
			ApiUpdateTripResponse.CONFLICT_RESOLUTION_IGNORED -> {
				val override = showDialogAndGetResponse(updateResponse.data.conflictInfo!!)
				if (override == null) {
					// do nothing and let user choose when he will use the app
					return null
				} else if (override) {
					localTrip.updatedAt = DateTimeHelper.now()
					// if request fails, user will not have to do the decision again
					tripsService.saveTrip(localTrip)
					val repeatedUpdateResponse = apiClient.updateTrip(
						localTrip.id,
						tripConverter.toApi(localTrip)
					).execute().body()!!
					return repeatedUpdateResponse.data!!.trip
				}
			}
		}

		return updateResponse.data!!.trip
	}
}
