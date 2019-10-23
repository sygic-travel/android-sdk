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
import org.threeten.bp.Instant
import org.threeten.bp.OffsetDateTime
import retrofit2.HttpException
import timber.log.Timber

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

		for (tripId in tripsService.findAllChanged()) {
			syncLocalChangedTrip(tripId, syncResult)
		}
	}

	private fun syncApiChangedTrip(apiTrip: ApiTripItemResponse, syncResult: SynchronizationResult) {
		val localTrip = tripsService.getTrip(apiTrip.id)

		if (localTrip == null) {
			createLocalTrip(apiTrip, syncResult)

		} else if (!localTrip.isChanged) {
			updateLocalTrip(apiTrip, syncResult)

		} else {
			// we throw away the server's data and try to push our changes first
			// server will try a data merge
			updateServerTrip(localTrip, syncResult)
		}
	}

	private fun syncLocalChangedTrip(tripId: String, syncResult: SynchronizationResult) {
		val localTrip = tripsService.getTrip(tripId) ?: return
		if (localTrip.isLocal()) {
			createServerTrip(localTrip, syncResult)
		} else {
			updateServerTrip(localTrip, syncResult)
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

	private fun createServerTrip(_localTrip: Trip, syncResult: SynchronizationResult) {
		Timber.i("Creating trip ${_localTrip.id}")

		val localTrip = sanitizeTrip(_localTrip)
		val createResponse = apiClient.createTrip(tripConverter.toApi(localTrip)).checkedExecute()

		val trip = createResponse.body()!!.data!!.trip
		syncResult.createdTripIdsMapping[localTrip.id] = trip.id
		val newTripId = trip.id
		tripsService.replaceTripId(localTrip, newTripId)
		tripsService.updateTrip(tripConverter.fromApi(trip))
		tripIdUpdateHandler?.invoke(newTripId, trip.id)
	}

	private fun updateServerTrip(_localTrip: Trip, syncResult: SynchronizationResult) {
		Timber.i("Updating trip ${_localTrip.id}")

		val localTrip = sanitizeTrip(_localTrip)
		val updateResponse = apiClient.updateTrip(localTrip.id, tripConverter.toApi(localTrip)).execute()

		if (updateResponse.code() == 404) {
			Timber.w("Trip ${localTrip.id} deleted on server, removing in local store.")
			tripsService.deleteTrip(localTrip.id)
			syncResult.changedTripIds.add(localTrip.id)
			return

		} else if (updateResponse.code() == 403) {
			Timber.w("Trip ${localTrip.id} is not allowed to be modified by current user, trying to fetch original.")
			val tripResponse = apiClient.getTrip(localTrip.id).execute()
			if (tripResponse.isSuccessful) {
				val apiTripData = tripResponse.body()!!.data!!.trip
				Timber.w("Trip ${localTrip.id} is not allowed to be modified by current user; re-fetched.")
				updateLocalTrip(apiTripData, syncResult)
			}
			return

		} else if (!updateResponse.isSuccessful) {
			throw HttpException(updateResponse)
		}

		val data = updateResponse.body()!!.data!!
		val apiTripData = data.trip
		when (data.conflict_resolution) {
			ApiUpdateTripResponse.CONFLICT_RESOLUTION_IGNORED -> {
				Timber.w("Trip ${localTrip.id} has conflict: ${data.conflict_resolution}; ${data.conflict_info}")
				val conflictResolution = when (val conflictHandler = tripUpdateConflictHandler) {
					null -> TripConflictResolution.USE_SERVER_VERSION
					else -> {
						val conflictInfo = TripConflictInfo(
							localTrip = localTrip,
							remoteTrip = tripConverter.fromApi(apiTripData),
							remoteTripUserName = data.conflict_info!!.last_user_name,
							remoteTripUpdatedAt = OffsetDateTime.parse(data.conflict_info.last_updated_at).toInstant()
						)
						conflictHandler.invoke(conflictInfo)
					}
				}

				Timber.i("Trip ${localTrip.id} conflict resolution: $conflictResolution")
				when (conflictResolution) {
					TripConflictResolution.NO_ACTION -> {
						// do nothing and let user choose when he will use the app
						return
					}
					TripConflictResolution.USE_LOCAL_VERSION -> {
						val forcedLocalTrip = localTrip.copy(updatedAt = Instant.now())
						// if request fails, user will not have to do the decision again
						tripsService.updateTrip(forcedLocalTrip)
						val repeatedUpdateResponse = apiClient.updateTrip(forcedLocalTrip.id, tripConverter.toApi(forcedLocalTrip)).checkedExecute()
						if (repeatedUpdateResponse.body()!!.data!!.conflict_resolution != ApiUpdateTripResponse.CONFLICT_RESOLUTION_IGNORED) {
							// update may result in ignored change, so the user's choice to keep local version cannot be
							// replaced by server version; the conflict will be resolved in the next synchronization run
							val updatedApiTripData = repeatedUpdateResponse.body()!!.data!!.trip
							updateLocalTrip(updatedApiTripData, syncResult)
						}
					}
					TripConflictResolution.USE_SERVER_VERSION -> {
						updateLocalTrip(apiTripData, syncResult)
					}
				}
			}
			ApiUpdateTripResponse.CONFLICT_RESOLUTION_MERGED, ApiUpdateTripResponse.CONFLICT_RESOLUTION_OVERRODE -> {
				Timber.w("Trip ${localTrip.id} had conflict: ${data.conflict_resolution}; ${data.conflict_info}")
				updateLocalTrip(apiTripData, syncResult)
			}
			ApiUpdateTripResponse.NO_CONFLICT -> {
				// propagate an update with new trip's version property
				updateLocalTrip(apiTripData, syncResult)
			}
		}
	}

	private fun sanitizeTrip(trip: Trip): Trip {
		if (trip.getLocalPlaceIds().isEmpty()) {
			return trip
		}

		Timber.e("Removing local places from trip ${trip.id} to allow synchronization.")
		return trip.copy(
			days = trip.days.map { tripDay ->
				tripDay.copy(itinerary = tripDay.itinerary.filter { it.placeId.startsWith("*") })
			}
		)
	}
}
