package com.sygic.travel.sdk.db

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters
import com.sygic.travel.sdk.db.dao.FavoriteDao
import com.sygic.travel.sdk.model.place.Favorite

@Database(
	entities = arrayOf(Favorite::class),
	version = 1
)
@TypeConverters(Converters::class)
internal abstract class StDb : RoomDatabase() {
	abstract fun favoriteDao(): FavoriteDao
}
