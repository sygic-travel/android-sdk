package com.sygic.travel.sdk.synchronization.model

class SynchronizationResult(
	val changedTripIds: Set<String>,
	val changedFavoriteIds: Set<String>,
	val cratedTripIdsMapping: Map<String, String>
)
