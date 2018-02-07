package com.sygic.travel.sdk.events.facades

import com.sygic.travel.sdk.session.model.Session
import com.sygic.travel.sdk.session.service.SessionService
import com.sygic.travel.sdk.synchronization.model.SynchronizationResult
import com.sygic.travel.sdk.synchronization.model.TripConflictInfo
import com.sygic.travel.sdk.synchronization.model.TripConflictResolution
import com.sygic.travel.sdk.synchronization.services.SynchronizationService
import com.sygic.travel.sdk.synchronization.services.TripsSynchronizationService

/**
 * Events facade provides an centralized access point for subscribing to SDK's events.
 */
@Suppress("MemberVisibilityCanBePrivate", "unused")
class EventsFacade internal constructor(
	private val sessionService: () -> SessionService,
	private val tripsSynchronizationService: () -> TripsSynchronizationService,
	private val synchronizationService: () -> SynchronizationService
) {
	var sessionUpdateHandler: ((session: Session?) -> Unit)?
		get() {
			return sessionService().sessionUpdateHandler
		}
		set(value) {
			sessionService().sessionUpdateHandler = value
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
