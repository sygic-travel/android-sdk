package com.sygic.travel.sdk.db.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.sygic.travel.sdk.model.place.Favorite
@Dao
interface FavoriteDao {
	@Query("SELECT * FROM favorite")
	fun getAll(): List<Favorite>

	@Insert(onConflict = OnConflictStrategy.IGNORE)
	fun insert(vararg placeId: Favorite)

	@Insert(onConflict = OnConflictStrategy.IGNORE)
	fun insert(placeId: List<Favorite>)
}
