package com.sygic.travel.sdk.synchronization.model

class SynchronizationResult(
	val changedTripIds: MutableSet<String> = mutableSetOf(),
	val changedFavoriteIds: MutableSet<String> = mutableSetOf(),
	val cratedTripIdsMapping: MutableMap<String, String> = mutableMapOf()
)
