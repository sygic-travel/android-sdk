package com.sygic.travel.sdk.places.model

import com.sygic.travel.sdk.places.model.geo.Bounds
import com.sygic.travel.sdk.places.model.geo.Location

class Place {
	var id: String? = null
	var level: String? = null
	var categories: List<String>? = null
	var rating: Float = 0.toFloat()
	var quadkey: String? = null
	var location: Location? = null
	var bounds: Bounds? = null
	var name: String? = null
	var nameSuffix: String? = null
	var perex: String? = null
	var url: String? = null
	var thumbnailUrl: String? = null
	var marker: String? = null
	var parentIds: List<String>? = null
	var detail: Detail? = null

	fun hasLocation(): Boolean {
		return location != null && (location!!.lat.compareTo(0.0f) != 0 || location!!.lng.compareTo(0.0f) != 0)
	}

	fun hasThumbnailUrl(): Boolean {
		return thumbnailUrl != null && thumbnailUrl != ""
	}
}
