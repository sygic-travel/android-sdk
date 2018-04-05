package com.sygic.travel.sdk.favorites.model.daos

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Delete
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import android.arch.persistence.room.Update
import com.sygic.travel.sdk.favorites.model.Favorite

/**
 * Interface containing methods for working with favorite table in database.
 */
@Dao
internal interface FavoriteDao {
	/**
	 * @return All favorite places' ids.
	 */
	@Query("SELECT * FROM favorites WHERE state < 2")
	fun findAll(): List<Favorite>

	@Query("SELECT * FROM favorites WHERE state > 0")
	fun findForSynchronization(): List<Favorite>

	@Query("SELECT COUNT(*) FROM favorites WHERE state > 0")
	fun getAllChangedCount(): Int

	@Query("SELECT * FROM favorites WHERE id = :id")
	fun get(id: String): Favorite?

	@Insert(onConflict = OnConflictStrategy.REPLACE)
	fun insert(favorite: Favorite)

	@Update
	fun update(favorite: Favorite)

	@Delete
	fun delete(favorite: Favorite)

	@Query("DELETE FROM favorites")
	fun deleteAll()
}
