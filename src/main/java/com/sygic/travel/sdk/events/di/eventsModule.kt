package com.sygic.travel.sdk.events.di

import com.sygic.travel.sdk.session.service.SessionService
import com.sygic.travel.sdk.events.facades.EventsFacade
import com.sygic.travel.sdk.synchronization.services.SynchronizationService
import com.sygic.travel.sdk.synchronization.services.TripsSynchronizationService
import org.kodein.di.Kodein
import org.kodein.di.erased.bind
import org.kodein.di.erased.instance
import org.kodein.di.erased.singleton

internal val eventsModule = Kodein.Module("eventsModule") {
	bind<EventsFacade>() with singleton {
		EventsFacade(
			{ instance<SessionService>() },
			{ instance<TripsSynchronizationService>() },
			{ instance<SynchronizationService>() }
		)
	}
}
