package com.sygic.travel.sdk.common.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters
import com.sygic.travel.sdk.favorites.model.Favorite
import com.sygic.travel.sdk.favorites.model.daos.FavoriteDao
import com.sygic.travel.sdk.trips.database.daos.TripDayItemsDao
import com.sygic.travel.sdk.trips.database.daos.TripDaysDao
import com.sygic.travel.sdk.trips.database.daos.TripsDao
import com.sygic.travel.sdk.trips.database.entities.Trip
import com.sygic.travel.sdk.trips.database.entities.TripDay
import com.sygic.travel.sdk.trips.database.entities.TripDayItem

@Database(
	entities = [
		Favorite::class,
		Trip::class,
		TripDay::class,
		TripDayItem::class
	],
	version = 3
)
@TypeConverters(Converters::class)
internal abstract class Database : RoomDatabase() {
	abstract fun favoriteDao(): FavoriteDao
	abstract fun tripsDao(): TripsDao
	abstract fun tripDaysDao(): TripDaysDao
	abstract fun tripDayItemsDao(): TripDayItemsDao
}
