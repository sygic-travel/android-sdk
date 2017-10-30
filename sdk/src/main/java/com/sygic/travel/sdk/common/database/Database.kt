package com.sygic.travel.sdk.common.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters
import com.sygic.travel.sdk.favorites.model.Favorite
import com.sygic.travel.sdk.favorites.model.daos.FavoriteDao

@Database(
	entities = arrayOf(Favorite::class),
	version = 1
)
@TypeConverters(Converters::class)
abstract class Database : RoomDatabase() {
	abstract fun favoriteDao(): FavoriteDao
}
