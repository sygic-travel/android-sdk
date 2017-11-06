package com.sygic.travel.sdk.synchronization.facades

import com.sygic.travel.sdk.Callback
import com.sygic.travel.sdk.synchronization.services.SynchronizationService
import com.sygic.travel.sdk.utils.runAsync
import com.sygic.travel.sdk.utils.runWithCallback

class SynchronizationFacade(
	private val synchronizationService: SynchronizationService
) {
	fun setTripUpdateConflictCallback() {
		TODO()
	}

	fun synchronize(callback: Callback<Unit>) {
		runWithCallback({ synchronizationService.synchronize() }, callback)
	}

	suspend fun synchronize(): Unit {
		return runAsync { synchronizationService.synchronize() }
	}
}
