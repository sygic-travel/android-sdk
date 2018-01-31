package com.sygic.travel.sdk.synchronization.services

import com.sygic.travel.sdk.common.api.SygicTravelApiClient
import com.sygic.travel.sdk.synchronization.model.SynchronizationResult
import com.sygic.travel.sdk.trips.api.TripConverter
import com.sygic.travel.sdk.trips.api.model.ApiTripItemResponse
import com.sygic.travel.sdk.trips.api.model.ApiUpdateTripResponse
import com.sygic.travel.sdk.trips.model.Trip
import com.sygic.travel.sdk.trips.services.TripsService
import com.sygic.travel.sdk.utils.DateTimeHelper

internal class TripsSynchronizationService constructor(
	private val apiClient: SygicTravelApiClient,
	private val tripConverter: TripConverter,
	private val tripsService: TripsService
) {
	var tripIdUpdateHandler: ((oldTripId: String, newTripId: String) -> Unit)? = null
	fun sync(changedTripIds: List<String>, deletedTripIds: List<String>): SynchronizationResult.TripsResult {
		val changedTrips = if (changedTripIds.isNotEmpty()) {
			apiClient.getTrips(
				changedTripIds.joinToString("|")
			).execute().body()!!.data?.trips ?: listOf()
		} else {
			listOf()
		}

		for (deletedTripId in deletedTripIds) {
			tripsService.deleteTrip(deletedTripId)
		}

		val syncResult = TripSynchronizationResult(
			removed = deletedTripIds.toMutableList()
		)

		for (trip in changedTrips) {
			syncApiChangedTrip(trip, syncResult)
		}

		syncLocalChangedTrips(syncResult)

		return syncResult.asTripsResult()
	}

	private fun showDialogAndGetResponse(conflictInfo: ApiUpdateTripResponse.ConflictInfo): Boolean? {
		return false // todo
	}

	private fun syncApiChangedTrip(apiTrip: ApiTripItemResponse, syncResult: TripSynchronizationResult) {
		val localTrip = tripsService.getTrip(apiTrip.id)

		if (localTrip == null) {
			createLocalTrip(apiTrip, syncResult)

		} else if (!localTrip.isChanged) {
			updateLocalTrip(apiTrip, syncResult)

		} else {
			// we throw away the server data and try to push our changes first
			// server will try a data merge
			updateServerTrip(localTrip, syncResult)
		}
	}

	private fun syncLocalChangedTrips(syncResult: TripSynchronizationResult) {
		tripsService.findAllChanged().forEach { trip ->
			if (trip.isLocal()) {
				createServerTrip(trip, syncResult)
			} else {
				updateServerTrip(trip, syncResult)
			}
		}
	}

	private fun createLocalTrip(apiTrip: ApiTripItemResponse, syncResult: TripSynchronizationResult) {
		val localTrip = tripConverter.fromApi(apiTrip)
		tripsService.saveTrip(localTrip)
		syncResult.added.add(localTrip.id)
	}

	private fun updateLocalTrip(apiTrip: ApiTripItemResponse, syncResult: TripSynchronizationResult) {
		val localTrip = tripConverter.fromApi(apiTrip)
		tripsService.saveTrip(localTrip)
		syncResult.changed.add(localTrip.id)
	}

	private fun createServerTrip(localTrip: Trip, syncResult: TripSynchronizationResult) {
		val response = apiClient.createTrip(tripConverter.toApi(localTrip)).execute().body()!!
		val trip = response.data!!.trip
		syncResult.createdOnServerIdMap[localTrip.id] = trip.id
		tripsService.replaceTripId(localTrip, trip.id)
		tripsService.saveTrip(tripConverter.fromApi(trip))
		tripIdUpdateHandler?.invoke(localTrip.id, trip.id)
	}

	private fun updateServerTrip(localTrip: Trip, syncResult: TripSynchronizationResult) {
		val updateResponse = apiClient.updateTrip(localTrip.id, tripConverter.toApi(localTrip)).execute()

		if (updateResponse.code() == 404) {
			tripsService.deleteTrip(localTrip.id)
			syncResult.removed.add(localTrip.id)
			return
		}

		val data = updateResponse.body()!!.data!!
		var apiTripData = data.trip
		when (data.conflictResolution) {
			ApiUpdateTripResponse.CONFLICT_RESOLUTION_IGNORED -> {
				val override = showDialogAndGetResponse(data.conflictInfo!!)
				if (override == null) {
					// do nothing and let user choose when he will use the app
					return
				} else if (override) {
					localTrip.updatedAt = DateTimeHelper.now()
					// if request fails, user will not have to do the decision again
					tripsService.saveTrip(localTrip)
					val repeatedUpdateResponse = apiClient.updateTrip(
						localTrip.id,
						tripConverter.toApi(localTrip)
					).execute().body()!!
					apiTripData = repeatedUpdateResponse.data!!.trip
					updateLocalTrip(apiTripData, syncResult)
				}
			}
			ApiUpdateTripResponse.CONFLICT_RESOLUTION_MERGED -> {
				updateLocalTrip(apiTripData, syncResult)
			}
			else -> {
				// do not track a change, restore isChanged to false
				tripsService.saveTrip(tripConverter.fromApi(apiTripData))
			}
		}
	}

	private class TripSynchronizationResult(
		val added: MutableList<String> = mutableListOf(),
		val changed: MutableList<String> = mutableListOf(),
		val removed: MutableList<String> = mutableListOf(),
		val createdOnServerIdMap: MutableMap<String, String> = mutableMapOf()
	) {
		fun asTripsResult(): SynchronizationResult.TripsResult {
			return SynchronizationResult.TripsResult(
				added = added,
				updated = changed,
				removed = removed,
				createdOnServerIdMap = createdOnServerIdMap
			)
		}
	}
}
