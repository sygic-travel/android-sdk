package com.sygic.travel.sdk.db.dao

import android.arch.persistence.room.*
import com.sygic.travel.sdk.model.place.Favorite
@Dao
interface FavoriteDao {
	@Query("SELECT * FROM favorite")
	fun loadAll(): List<Favorite>

	@Insert(onConflict = OnConflictStrategy.IGNORE)
	fun insert(placeId: Favorite) : Long

	@Delete
	fun delete(placeId: Favorite) : Int
}
