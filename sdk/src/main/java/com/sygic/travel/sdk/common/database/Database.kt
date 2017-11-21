package com.sygic.travel.sdk.common.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters
import com.sygic.travel.sdk.favorites.model.Favorite
import com.sygic.travel.sdk.favorites.model.daos.FavoriteDao
import com.sygic.travel.sdk.trips.model.TripDay
import com.sygic.travel.sdk.trips.model.TripDayItem
import com.sygic.travel.sdk.trips.model.TripInfo
import com.sygic.travel.sdk.trips.model.daos.TripDayItemsDao
import com.sygic.travel.sdk.trips.model.daos.TripDaysDao
import com.sygic.travel.sdk.trips.model.daos.TripsDao

@Database(
	entities = arrayOf(
		Favorite::class,
		TripInfo::class,
		TripDay::class,
		TripDayItem::class
	),
	version = 2
)
@TypeConverters(Converters::class)
abstract internal class Database : RoomDatabase() {
	abstract fun favoriteDao(): FavoriteDao
	abstract fun tripsDao(): TripsDao
	abstract fun tripDaysDao(): TripDaysDao
	abstract fun tripDayItemsDao(): TripDayItemsDao
}
