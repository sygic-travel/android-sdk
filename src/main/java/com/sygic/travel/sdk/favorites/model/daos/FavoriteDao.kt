package com.sygic.travel.sdk.favorites.model.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
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
	fun replace(favorite: Favorite)

	@Update
	fun update(favorite: Favorite)

	@Delete
	fun delete(favorite: Favorite)

	@Query("DELETE FROM favorites")
	fun deleteAll()
}
