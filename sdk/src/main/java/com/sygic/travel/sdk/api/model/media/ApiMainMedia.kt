package com.sygic.travel.sdk.api.model.media

import com.google.gson.annotations.SerializedName
import com.sygic.travel.sdk.api.model.converter.ApiModel
import com.sygic.travel.sdk.model.media.MainMedia
import com.sygic.travel.sdk.model.media.Medium

/**
 * Place main media.
 */
internal class ApiMainMedia : ApiModel<MainMedia> {

	@SerializedName("usage")
	val usage: ApiUsage? = null

	@SerializedName("media")
	val media: List<ApiMedium>? = null

	override fun convert(): MainMedia {
		val mainMedia = MainMedia()

		val convertedMedia: MutableList<Medium> = mutableListOf()
		media?.mapTo(convertedMedia) {
			it.convert()
		}

		mainMedia.usage = usage?.convert()
		mainMedia.media = convertedMedia

		return mainMedia
	}
}
