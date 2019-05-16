package com.sygic.travel.sdk.favorites.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Database entity, representing a table with one column referencing to a place marked as favorite.
 */
@Entity(tableName = "favorites")
class Favorite {
	companion object {
		const val STATE_SYNCED = 0
		const val STATE_TO_ADD = 1
		const val STATE_TO_REMOVE = 2
	}

	@PrimaryKey
	lateinit var id: String

	@ColumnInfo
	var state: Int = 0
}
