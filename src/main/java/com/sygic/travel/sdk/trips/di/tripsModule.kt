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
import org.koin.dsl.module

internal val tripsModule = module {
	single {
		checkUserDataSupport(getProperty("userDataSupported"), "Trips")
		TripsFacade(get<TripsService>())
	}
	single {
		TripsService(
			get<SygicTravelApiClient>(),
			get<TripsDao>(),
			get<TripDaysDao>(),
			get<TripDayItemsDao>(),
			get<TripDbConverter>(),
			get<TripDayDbConverter>(),
			get<TripDayItemDbConverter>(),
			get<TripConverter>()
		)
	}
	single {
		get<Database>().tripsDao()
	}
	single {
		get<Database>().tripDaysDao()
	}
	single {
		get<Database>().tripDayItemsDao()
	}
	single {
		TripConverter(get<TripDayConverter>())
	}
	single {
		TripDayConverter(get<TripDayItemConverter>())
	}
	single {
		TripDayItemConverter(get<TripItemTransportConverter>())
	}
	single {
		TripItemTransportConverter()
	}
	single {
		TripDbConverter()
	}
	single {
		TripDayDbConverter()
	}
	single {
		TripDayItemDbConverter(get<TripDayItemTransportDbConverter>())
	}
	single {
		TripDayItemTransportDbConverter()
	}
}
