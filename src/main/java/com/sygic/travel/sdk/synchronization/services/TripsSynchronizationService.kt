package com.sygic.travel.sdk.synchronization.services

import com.sygic.travel.sdk.common.api.SygicTravelApiClient
import com.sygic.travel.sdk.common.api.checkedExecute
import com.sygic.travel.sdk.synchronization.model.SynchronizationResult
import com.sygic.travel.sdk.synchronization.model.TripConflictInfo
import com.sygic.travel.sdk.synchronization.model.TripConflictResolution
import com.sygic.travel.sdk.trips.api.TripConverter
import com.sygic.travel.sdk.trips.api.model.ApiTripItemResponse
import com.sygic.travel.sdk.trips.api.model.ApiUpdateTripResponse
import com.sygic.travel.sdk.trips.model.Trip
import com.sygic.travel.sdk.trips.services.TripsService
import com.sygic.travel.sdk.utils.DateTimeHelper
import retrofit2.HttpException

internal class TripsSynchronizationService constructor(
	private val apiClient: SygicTravelApiClient,
	private val tripConverter: TripConverter,
	private val tripsService: TripsService
) {
	var tripIdUpdateHandler: ((oldTripId: String, newTripId: String) -> Unit)? = null
	var tripUpdateConflictHandler: ((conflictInfo: TripConflictInfo) -> TripConflictResolution)? = null

	fun sync(changedTripIds: List<String>, deletedTripIds: List<String>, syncResult: SynchronizationResult) {
		val changedTrips = if (changedTripIds.isNotEmpty()) {
			val changesResponse = apiClient.getTrips(changedTripIds.joinToString("|")).checkedExecute()
			changesResponse.body()!!.data!!.trips
		} else {
			listOf()
		}

		for (deletedTripId in deletedTripIds) {
			tripsService.deleteTrip(deletedTripId)
		}

		syncResult.changedTripIds.addAll(deletedTripIds)

		for (trip in changedTrips) {
			syncApiChangedTrip(trip, syncResult)
		}

		syncLocalChangedTrips(syncResult)
	}

	private fun syncApiChangedTrip(apiTrip: ApiTripItemResponse, syncResult: SynchronizationResult) {
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

	private fun syncLocalChangedTrips(syncResult: SynchronizationResult) {
		tripsService.findAllChanged().forEach { trip ->
			if (trip.isLocal()) {
				createServerTrip(trip, syncResult)
			} else {
				updateServerTrip(trip, syncResult)
			}
		}
	}

	private fun createLocalTrip(apiTrip: ApiTripItemResponse, syncResult: SynchronizationResult) {
		val localTrip = tripConverter.fromApi(apiTrip)
		tripsService.createTrip(localTrip)
		syncResult.changedTripIds.add(localTrip.id)
	}

	private fun updateLocalTrip(apiTrip: ApiTripItemResponse, syncResult: SynchronizationResult) {
		val localTrip = tripConverter.fromApi(apiTrip)
		tripsService.updateTrip(localTrip)
		syncResult.changedTripIds.add(localTrip.id)
	}

	private fun createServerTrip(localTrip: Trip, syncResult: SynchronizationResult) {
		val createResponse = apiClient.createTrip(tripConverter.toApi(localTrip)).checkedExecute()
		val trip = createResponse.body()!!.data!!.trip
		syncResult.createdTripIdsMapping[localTrip.id] = trip.id
		tripsService.replaceTripId(localTrip, trip.id)
		tripsService.updateTrip(tripConverter.fromApi(trip))
		tripIdUpdateHandler?.invoke(localTrip.id, trip.id)
	}

	private fun updateServerTrip(localTrip: Trip, syncResult: SynchronizationResult) {
		val updateResponse = apiClient.updateTrip(localTrip.id, tripConverter.toApi(localTrip)).execute()

		if (updateResponse.code() == 404) {
			tripsService.deleteTrip(localTrip.id)
			syncResult.changedTripIds.add(localTrip.id)
			return

		} else if (updateResponse.code() == 403) {
			val tripResponse = apiClient.getTrip(localTrip.id).execute()
			if (tripResponse.isSuccessful) {
				val apiTripData = tripResponse.body()!!.data!!.trip
				updateLocalTrip(apiTripData, syncResult)
			}
			return

		} else if (!updateResponse.isSuccessful) {
			throw HttpException(updateResponse)
		}

		val data = updateResponse.body()!!.data!!
		var apiTripData = data.trip
		when (data.conflict_resolution) {
			ApiUpdateTripResponse.CONFLICT_RESOLUTION_IGNORED -> {
				val conflictHandler = tripUpdateConflictHandler
				val conflictResolution = when (conflictHandler) {
					null -> TripConflictResolution.USE_SERVER_VERSION
					else -> {
						val conflictInfo = TripConflictInfo(
							localTrip = localTrip,
							remoteTrip = tripConverter.fromApi(apiTripData),
							remoteTripUserName = data.conflict_info!!.last_user_name,
							remoteTripUpdatedAt = DateTimeHelper.datetimeToTimestamp(data.conflict_info.last_updated_at)!!
						)
						conflictHandler.invoke(conflictInfo)
					}
				}
				when (conflictResolution) {
					TripConflictResolution.NO_ACTION -> {
						// do nothing and let user choose when he will use the app
						return
					}
					TripConflictResolution.USE_LOCAL_VERSION -> {
						localTrip.updatedAt = DateTimeHelper.now()
						// if request fails, user will not have to do the decision again
						tripsService.updateTrip(localTrip)
						val repeatedUpdateResponse = apiClient.updateTrip(localTrip.id, tripConverter.toApi(localTrip)).checkedExecute()
						apiTripData = repeatedUpdateResponse.body()!!.data!!.trip
						updateLocalTrip(apiTripData, syncResult)
					}
					TripConflictResolution.USE_SERVER_VERSION -> {
						updateLocalTrip(apiTripData, syncResult)
					}
				}
			}
			ApiUpdateTripResponse.CONFLICT_RESOLUTION_MERGED, ApiUpdateTripResponse.CONFLICT_RESOLUTION_OVERRODE -> {
				updateLocalTrip(apiTripData, syncResult)
			}
			ApiUpdateTripResponse.NO_CONFLICT -> {
				// do not track a change, restore isChanged to false
				tripsService.updateTrip(tripConverter.fromApi(apiTripData))
			}
		}
	}
}
