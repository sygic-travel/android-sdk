package com.sygic.travel.sdk.trips.model

import java.util.UUID

/**
 * Trip entity representation.
 * It contains all metadata and days' definitions.
 */
@Suppress("ConvertSecondaryConstructorToPrimary")
class Trip : TripInfo {
	var days = mutableListOf<TripDay>()
	override var daysCount: Int = 0
		get() = days.size
		internal set

	/**
	 * Creates a new trip instance with a local ID.
	 */
	constructor() : super(UUID.randomUUID().toString())

	internal constructor(id: String) : super(id)

	fun getPlaceIds(): Set<String> {
		return days.map { it.getPlaceIds() }.flatten().toSet()
	}
}
