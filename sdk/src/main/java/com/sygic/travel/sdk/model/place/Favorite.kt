package com.sygic.travel.sdk.model.place

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "favorite")
class Favorite(
	@PrimaryKey
	var id: String? = null
)
