package com.sygic.travel.sdk.events.di

import com.sygic.travel.sdk.events.facades.EventsFacade
import com.sygic.travel.sdk.session.service.SessionService
import com.sygic.travel.sdk.synchronization.services.SynchronizationService
import com.sygic.travel.sdk.synchronization.services.TripsSynchronizationService
import org.koin.dsl.module.module

internal val eventsModule = module {
	single {
		EventsFacade(
			{ get<SessionService>() },
			{ get<TripsSynchronizationService>() },
			{ get<SynchronizationService>() }
		)
	}
}
