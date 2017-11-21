package com.sygic.travel.sdk.synchronization.facades

import com.sygic.travel.sdk.synchronization.services.SynchronizationService
import com.sygic.travel.sdk.utils.checkNotRunningOnMainThread

class SynchronizationFacade(
	private val synchronizationService: SynchronizationService
) {
	fun setTripUpdateConflictCallback() {
		TODO()
	}

	fun synchronize() {
		checkNotRunningOnMainThread()
		return synchronizationService.synchronize()
	}

	internal fun clearUserData() {
		checkNotRunningOnMainThread()
		synchronizationService.clearUserData()
	}
}
