package com.sygic.travel.sdk.events.facades

import com.sygic.travel.sdk.auth.model.UserSession
import com.sygic.travel.sdk.auth.service.AuthService
import com.sygic.travel.sdk.synchronization.model.SynchronizationResult
import com.sygic.travel.sdk.synchronization.model.TripConflictInfo
import com.sygic.travel.sdk.synchronization.model.TripConflictResolution
import com.sygic.travel.sdk.synchronization.services.SynchronizationService
import com.sygic.travel.sdk.synchronization.services.TripsSynchronizationService

class EventsFacade internal constructor(
	private val authService: () -> AuthService,
	private val tripsSynchronizationService: () -> TripsSynchronizationService,
	private val synchronizationService: () -> SynchronizationService
) {
	var sessionUpdateHandler: ((session: UserSession?) -> Unit)?
		get() {
			return authService().sessionUpdateHandler
		}
		set(value) {
			authService().sessionUpdateHandler = value
		}

	var tripIdUpdateHandler: ((oldTripId: String, newTripId: String) -> Unit)?
		get() {
			return tripsSynchronizationService().tripIdUpdateHandler
		}
		set(value) {
			tripsSynchronizationService().tripIdUpdateHandler = value
		}

	var tripUpdateConflictHandler: ((conflictInfo: TripConflictInfo) -> TripConflictResolution)?
		get() {
			return tripsSynchronizationService().tripUpdateConflictHandler
		}
		set(value) {
			tripsSynchronizationService().tripUpdateConflictHandler = value
		}

	var synchronizationCompletionHandler: ((result: SynchronizationResult) -> Unit)?
		get() {
			return synchronizationService().synchronizationCompletionHandler
		}
		set(value) {
			synchronizationService().synchronizationCompletionHandler = value
		}
}
