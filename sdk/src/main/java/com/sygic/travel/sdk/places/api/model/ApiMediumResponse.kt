package com.sygic.travel.sdk.places.api.model

import com.sygic.travel.sdk.common.api.model.ApiLocationResponse
import com.sygic.travel.sdk.places.model.media.Attribution
import com.sygic.travel.sdk.places.model.media.Medium
import com.sygic.travel.sdk.places.model.media.Original
import com.sygic.travel.sdk.places.model.media.Source

internal class ApiMediumResponse(
	val id: String,
	val type: String,
	val url_template: String,
	val url: String,
	val original: ApiOriginal,
	val suitability: List<String>,
	val created_at: String,
	val source: ApiSource,
	val created_by: String?,
	val attribution: ApiAttribution,
	val location: ApiLocationResponse?
) {
	class ApiOriginal(
		val size: Int?,
		val width: Int?,
		val height: Int?
	) {
		fun fromApi(): Original {
			val original = Original()
			original.size = size
			original.width = width
			original.height = height
			return original
		}
	}

	class ApiAttribution(
		val author: String?,
		val author_url: String?,
		val license: String?,
		val license_url: String?,
		val other: String?,
		val title: String?,
		val title_url: String?
	) {
		fun fromApi(): Attribution {
			val attribution = Attribution()
			attribution.author = author
			attribution.authorUrl = author_url
			attribution.license = license
			attribution.licenseUrl = license_url
			attribution.other = other
			attribution.title = title
			attribution.titleUrl = title_url
			return attribution
		}
	}

	class ApiSource(
		val provider: String,
		val name: String?,
		val external_id: String?
	) {
		fun fromApi(): Source {
			val source = Source()
			source.name = name
			source.externalId = external_id
			source.provider = provider
			return source
		}
	}

	fun fromApi(): Medium {
		val medium = Medium()
		medium.id = id
		medium.type = type
		medium.urlTemplate = url_template
		medium.url = url
		medium.original = original.fromApi()
		medium.suitability = suitability
		medium.createdAt = created_at
		medium.source = source.fromApi()
		medium.createdBy = created_by
		medium.attribution = attribution.fromApi()
		medium.location = location?.fromApi()
		return medium
	}
}
