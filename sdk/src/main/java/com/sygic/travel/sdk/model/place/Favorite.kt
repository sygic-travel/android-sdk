package com.sygic.travel.sdk.model.place

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

/**
 * Database entity, representing a table with one column referencing to a place marked as favorite.
 */
@Entity(tableName = "favorite")
class Favorite(
	@PrimaryKey
	var id: String? = null
)
