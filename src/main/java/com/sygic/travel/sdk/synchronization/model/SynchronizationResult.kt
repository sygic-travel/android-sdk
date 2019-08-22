package com.sygic.travel.sdk.synchronization.model

data class SynchronizationResult internal constructor(
	val changedTripIds: MutableSet<String> = mutableSetOf(),
	val createdTripIdsMapping: MutableMap<String, String> = mutableMapOf(),
	val changedFavoriteIds: MutableSet<String> = mutableSetOf(),
	val changedCustomPlaceIds: MutableSet<String> = mutableSetOf(),
	var updatedUserSettings: Boolean = false,
	var success: Boolean = true,
	var exception: Exception? = null
)
