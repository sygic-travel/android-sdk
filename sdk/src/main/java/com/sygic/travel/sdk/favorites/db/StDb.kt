package com.sygic.travel.sdk.favorites.db

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters
import com.sygic.travel.sdk.favorites.model.Favorite

@Database(
	entities = arrayOf(Favorite::class),
	version = 1
)
@TypeConverters(Converters::class)
abstract class StDb : RoomDatabase() {
	abstract fun favoriteDao(): FavoriteDao
}
