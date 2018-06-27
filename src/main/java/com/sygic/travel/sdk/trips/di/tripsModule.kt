package com.sygic.travel.sdk.trips.di

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
import com.sygic.travel.sdk.utils.checkUserDataSupport
import org.kodein.di.Kodein
import org.kodein.di.erased.bind
import org.kodein.di.erased.instance
import org.kodein.di.erased.singleton

internal val tripsModule = Kodein.Module("tripsModule") {
	bind<TripsFacade>() with singleton {
		checkUserDataSupport(instance<Boolean>("userDataSupported"), "Trips")
		TripsFacade(instance<TripsService>())
	}
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
