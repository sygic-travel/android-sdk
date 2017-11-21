package com.sygic.travel.sdk.places.api.model

import com.sygic.travel.sdk.places.model.media.MainMedia
import com.sygic.travel.sdk.places.model.media.Usage

internal class ApiMainMediaResponse(
	val usage: ApiUsage,
	val media: List<ApiMediumResponse>
) {
	fun fromApi(): MainMedia {
		val mainMedia = MainMedia()
		mainMedia.usage = usage.fromApi()
		mainMedia.media = media.map { it.fromApi() }
		return mainMedia
	}

	class ApiUsage(
		val square: String,
		val landscape: String,
		val portrait: String,
		val video_preview: String?
	) {
		fun fromApi(): Usage {
			val usage = Usage()
			usage.square = square
			usage.landscape = landscape
			usage.portrait = portrait
			usage.videoPreview = video_preview
			return usage
		}
	}
}
