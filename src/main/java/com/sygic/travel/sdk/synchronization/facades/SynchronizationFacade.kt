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
	fun synchronize() {
		return synchronizationService.synchronize()
	}

	fun hasChangesToSynchronize(): Boolean {
		checkNotRunningOnMainThread()
		return tripsService.hasChangesToSynchronize()
			|| favoritesService.hasChangesToSynchronize()
	}

	internal fun clearUserData() {
		checkNotRunningOnMainThread()
		synchronizationService.clearUserData()
	}
}
