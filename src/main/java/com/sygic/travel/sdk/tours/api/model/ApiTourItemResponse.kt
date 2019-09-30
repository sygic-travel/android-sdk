package com.sygic.travel.sdk.tours.api.model

import com.squareup.moshi.JsonClass
import com.sygic.travel.sdk.tours.model.Tour
import org.threeten.bp.Duration

@JsonClass(generateAdapter = true)
internal class ApiTourItemResponse(
	val id: String,
	val supplier: String,
	val title: String,
	val perex: String,
	val url: String,
	val rating: Float,
	val review_count: Int,
	val photo_url: String,
	val price: Float,
	val original_price: Float,
	val duration: String?,
	val duration_min: Int?,
	val duration_max: Int?,
	val flags: List<String>
) {
	fun fromApi(): Tour {
		return Tour(
			id = id,
			supplier = supplier,
			title = title,
			perex = perex,
			url = url,
			rating = rating,
			reviewCount = review_count,
			photoUrl = photo_url,
			price = price,
			originalPrice = original_price,
			duration = duration,
			durationMin = duration_min?.let { Duration.ofSeconds(it.toLong()) },
			durationMax = duration_max?.let { Duration.ofSeconds(it.toLong()) },
			flags = flags.toHashSet()
		)
	}
}
