package com.sygic.travel.sdk.places.api.model

import com.sygic.travel.sdk.common.api.model.ApiLocationResponse
import com.sygic.travel.sdk.places.model.PlaceInfo
import com.sygic.travel.sdk.places.model.geo.Bounds

open internal class ApiPlaceListItemResponse(
	val id: String,
	val level: String,
	val categories: List<String>,
	val rating: Float,
	val quadkey: String,
	val location: ApiLocationResponse,
	val bounding_box: ApiBounds?,
	val name: String,
	val name_suffix: String?,
	val perex: String?,
	val url: String?,
	val thumbnail_url: String?,
	val marker: String?,
	val parent_ids: List<String>
) {
	class ApiBounds(
		val north: Float,
		val east: Float,
		val south: Float,
		val west: Float
	) {
		fun fromApi(): Bounds {
			val bounds = Bounds()
			bounds.north = north
			bounds.east = east
			bounds.south = south
			bounds.west = west
			return bounds
		}
	}

	open fun fromApi(): PlaceInfo {
		val place = PlaceInfo()
		fromApi(place)
		return place
	}

	protected fun fromApi(place: PlaceInfo) {
		place.id = id
		place.level = level
		place.categories = categories
		place.rating = rating
		place.quadkey = quadkey
		place.location = location.fromApi()
		place.bounds = bounding_box?.fromApi()
		place.name = name
		place.nameSuffix = name_suffix
		place.perex = perex
		place.url = url
		place.thumbnailUrl = thumbnail_url
		place.marker = marker
		place.parentIds = parent_ids
	}
}
