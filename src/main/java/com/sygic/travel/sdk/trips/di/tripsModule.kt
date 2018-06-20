package com.sygic.travel.sdk.trips.di

import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.bind
import com.github.salomonbrys.kodein.instance
import com.github.salomonbrys.kodein.singleton
import com.sygic.travel.sdk.common.api.SygicTravelApiClient
import com.sygic.travel.sdk.common.database.Database
import com.sygic.travel.sdk.trips.api.TripConverter
import com.sygic.travel.sdk.trips.api.TripDayConverter
import com.sygic.travel.sdk.trips.api.TripDayItemConverter
import com.sygic.travel.sdk.trips.api.TripItemTransportConverter
import com.sygic.travel.sdk.trips.database.converters.TripDayDbConverter
import com.sygic.travel.sdk.trips.database.converters.TripDayItemDbConverter
import com.sygic.travel.sdk.trips.database.converters.TripDayItemTransportDbConverter
import com.sygic.travel.sdk.trips.database.converters.TripDbConverter
import com.sygic.travel.sdk.trips.database.daos.TripDayItemsDao
import com.sygic.travel.sdk.trips.database.daos.TripDaysDao
import com.sygic.travel.sdk.trips.database.daos.TripsDao
import com.sygic.travel.sdk.trips.facades.TripsFacade
import com.sygic.travel.sdk.trips.services.TripsService

internal val tripsModule = Kodein.Module {
	bind<TripsFacade>() with singleton { TripsFacade(instance<TripsService>()) }
	bind<TripsService>() with singleton {
		TripsService(
			instance<SygicTravelApiClient>(),
			instance<TripsDao>(),
			instance<TripDaysDao>(),
			instance<TripDayItemsDao>(),
			instance<TripDbConverter>(),
			instance<TripDayDbConverter>(),
			instance<TripDayItemDbConverter>(),
			instance<TripConverter>()
		)
	}
	bind<TripsDao>() with singleton { instance<Database>().tripsDao() }
	bind<TripDaysDao>() with singleton { instance<Database>().tripDaysDao() }
	bind<TripDayItemsDao>() with singleton { instance<Database>().tripDayItemsDao() }
	bind<TripConverter>() with singleton { TripConverter(instance<TripDayConverter>()) }
	bind<TripDayConverter>() with singleton { TripDayConverter(instance<TripDayItemConverter>()) }
	bind<TripDayItemConverter>() with singleton { TripDayItemConverter(instance<TripItemTransportConverter>()) }
	bind<TripItemTransportConverter>() with singleton { TripItemTransportConverter() }
	bind<TripDbConverter>() with singleton { TripDbConverter() }
	bind<TripDayDbConverter>() with singleton { TripDayDbConverter() }
	bind<TripDayItemDbConverter>() with singleton { TripDayItemDbConverter(instance<TripDayItemTransportDbConverter>()) }
	bind<TripDayItemTransportDbConverter>() with singleton { TripDayItemTransportDbConverter() }
}
