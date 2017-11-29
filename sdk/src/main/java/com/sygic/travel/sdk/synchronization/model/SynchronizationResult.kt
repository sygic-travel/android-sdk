package com.sygic.travel.sdk.synchronization.model

class SynchronizationResult(
	val trips: TripsResult,
	val favorites: FavoritesResult
) {
	class TripsResult(
		val added: List<String>,
		val updated: List<String>,
		val removed: List<String>,
		val createdOnServerIdMap: Map<String, String>
	)

	class FavoritesResult(
		val added: List<String>,
		val removed: List<String>
	)
}
