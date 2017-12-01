package com.sygic.travel.sdk.synchronization.facades

import com.sygic.travel.sdk.favorites.service.FavoriteService
import com.sygic.travel.sdk.synchronization.model.SynchronizationResult
import com.sygic.travel.sdk.synchronization.services.SynchronizationService
import com.sygic.travel.sdk.trips.services.TripsService
import com.sygic.travel.sdk.utils.checkNotRunningOnMainThread

class SynchronizationFacade internal constructor(
	private val synchronizationService: SynchronizationService,
	private val tripsService: TripsService,
	private val favoritesService: FavoriteService
) {
	fun setTripUpdateConflictCallback() {
		TODO()
	}

	fun synchronize(): SynchronizationResult {
		checkNotRunningOnMainThread()
		return synchronizationService.synchronize()
	}

	fun hasChangesToSynchronization(): Boolean {
		checkNotRunningOnMainThread()
		return tripsService.hasChangesToSynchronization()
			|| favoritesService.hasChangesToSynchronization()
	}

	internal fun clearUserData() {
		checkNotRunningOnMainThread()
		synchronizationService.clearUserData()
	}
}
