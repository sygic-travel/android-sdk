package com.sygic.travel.sdk.events.di

import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.bind
import com.github.salomonbrys.kodein.instance
import com.github.salomonbrys.kodein.singleton
import com.sygic.travel.sdk.session.service.SessionService
import com.sygic.travel.sdk.events.facades.EventsFacade
import com.sygic.travel.sdk.synchronization.services.SynchronizationService
import com.sygic.travel.sdk.synchronization.services.TripsSynchronizationService

internal val eventsModule = Kodein.Module {
	bind<EventsFacade>() with singleton {
		EventsFacade(
			{ instance<SessionService>() },
			{ instance<TripsSynchronizationService>() },
			{ instance<SynchronizationService>() }
		)
	}
}
