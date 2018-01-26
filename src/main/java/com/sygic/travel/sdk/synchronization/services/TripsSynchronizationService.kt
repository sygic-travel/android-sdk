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

		val added = mutableListOf<String>()
		val updated = mutableListOf<String>()
		val deleted = deletedTripIds.toMutableList()

		for (trip in changedTrips) {
			val isAdded = syncApiChangedTrip(trip)
			when (isAdded) {
				true -> added.add(trip.id)
				false -> updated.add(trip.id)
			}
		}

		val created = syncLocalChangedTrips(changedTripIds)

		return SynchronizationResult.TripsResult(
			added = added,
			updated = updated,
			removed = deleted,
			createdOnServerIdMap = created
		)
	}

	private fun showDialogAndGetResponse(conflictInfo: ApiUpdateTripResponse.ConflictInfo): Boolean? {
		return false // todo
	}

	private fun syncApiChangedTrip(apiTrip: ApiTripItemResponse): Boolean {
		var localTrip = tripsService.getTrip(apiTrip.id)
		val isAdded = localTrip == null

		if (localTrip?.isChanged == true) {
			updateTrip(localTrip)
		} else {
			localTrip = tripConverter.fromApi(apiTrip)
			tripsService.saveTrip(localTrip)
		}

		return isAdded
	}

	private fun syncLocalChangedTrips(apiChangedTripIds: List<String>): Map<String, String> {
		val createdTripIdsMap = mutableMapOf<String, String>()
		val changedTrips = tripsService.findAllChangedExceptApiChanged(apiChangedTripIds)
		for (changedTrip in changedTrips) {
			syncLocalChangedTrip(changedTrip, createdTripIdsMap)
		}
		return createdTripIdsMap
	}

	private fun syncLocalChangedTrip(trip: Trip, createdTripIdsMap: MutableMap<String, String>) {
		if (trip.isLocal()) {
			val createResponse = apiClient.createTrip(
				tripConverter.toApi(trip)
			).execute().body()!!
			createdTripIdsMap[trip.id] = createResponse.data!!.trip.id
			tripsService.replaceTripId(trip, createResponse.data.trip.id)
			val updatedTrip = tripConverter.fromApi(createResponse.data.trip)
			tripsService.saveTrip(updatedTrip)

		} else {
			updateTrip(trip)
		}
	}

	private fun updateTrip(localTrip: Trip) {
		val updateResponse = apiClient.updateTrip(
			localTrip.id,
			tripConverter.toApi(localTrip)
		).execute().body()!!

		var apiTripData = updateResponse.data!!.trip
		when (updateResponse.data.conflictResolution) {
			ApiUpdateTripResponse.CONFLICT_RESOLUTION_IGNORED -> {
				val override = showDialogAndGetResponse(updateResponse.data.conflictInfo!!)
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
				}
			}
		}

		val updatedTrip = tripConverter.fromApi(apiTripData)
		tripsService.saveTrip(updatedTrip)
	}
}
