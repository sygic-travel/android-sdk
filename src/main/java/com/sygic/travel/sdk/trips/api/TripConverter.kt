package com.sygic.travel.sdk.trips.api

import com.sygic.travel.sdk.trips.api.model.ApiTripItemRequest
import com.sygic.travel.sdk.trips.api.model.ApiTripItemResponse
import com.sygic.travel.sdk.trips.api.model.ApiTripListItemResponse
import com.sygic.travel.sdk.trips.model.Trip
import com.sygic.travel.sdk.trips.model.TripInfo
import com.sygic.travel.sdk.trips.model.TripMedia
import com.sygic.travel.sdk.trips.model.TripPrivacyLevel
import com.sygic.travel.sdk.trips.model.TripPrivileges
import com.sygic.travel.sdk.utils.DateTimeHelper
import com.sygic.travel.sdk.utils.asDate
import com.sygic.travel.sdk.utils.timeSeconds

@Suppress("DEPRECATION")
internal class TripConverter constructor(
	private val tripDayConverter: TripDayConverter
) {
	fun fromApi(apiTrip: ApiTripListItemResponse): TripInfo {
		val localTrip = TripInfo(apiTrip.id)
		localTrip.ownerId = apiTrip.owner_id
		localTrip.name = apiTrip.name
		localTrip.version = apiTrip.version
		localTrip.url = apiTrip.url
		localTrip.isUserSubscribed = apiTrip.user_is_subscribed
		localTrip.updatedAt = DateTimeHelper.datetimeToTimestamp(apiTrip.updated_at)!!.asDate()
		localTrip.isDeleted = apiTrip.is_deleted
		localTrip.privacyLevel = fromApiPrivacyLevel(apiTrip.privacy_level)
		localTrip.startsOn = DateTimeHelper.dateToTimestamp(apiTrip.starts_on)?.asDate()
		localTrip.daysCount = apiTrip.day_count
		localTrip.media = fromApiMedia(apiTrip.media)
		localTrip.privileges = TripPrivileges(
			edit = apiTrip.privileges.edit,
			manage = apiTrip.privileges.manage,
			delete = apiTrip.privileges.delete
		)
		return localTrip
	}

	fun fromApi(apiTrip: ApiTripItemResponse): Trip {
		val localTrip = Trip(apiTrip.id)
		localTrip.ownerId = apiTrip.owner_id
		localTrip.name = apiTrip.name
		localTrip.version = apiTrip.version
		localTrip.url = apiTrip.url
		localTrip.isUserSubscribed = apiTrip.user_is_subscribed
		localTrip.updatedAt = DateTimeHelper.datetimeToTimestamp(apiTrip.updated_at)!!.asDate()
		localTrip.isDeleted = apiTrip.is_deleted
		localTrip.privacyLevel = fromApiPrivacyLevel(apiTrip.privacy_level)
		localTrip.startsOn = DateTimeHelper.dateToTimestamp(apiTrip.starts_on)?.asDate()
		localTrip.daysCount = apiTrip.day_count
		localTrip.media = fromApiMedia(apiTrip.media)
		localTrip.destinations = ArrayList(apiTrip.destinations)
		localTrip.days = apiTrip.days.map { tripDayConverter.fromApi(it) }
		localTrip.privileges = TripPrivileges(
			edit = apiTrip.privileges.edit,
			manage = apiTrip.privileges.manage,
			delete = apiTrip.privileges.delete
		)
		return localTrip
	}

	fun toApi(localTrip: Trip): ApiTripItemRequest {
		return ApiTripItemRequest(
			name = localTrip.name,
			base_version = localTrip.version,
			updated_at = DateTimeHelper.timestampToDatetime(localTrip.updatedAt!!.timeSeconds)!!,
			is_deleted = localTrip.isDeleted,
			privacy_level = when (localTrip.privacyLevel) {
				TripPrivacyLevel.PUBLIC -> ApiTripListItemResponse.PRIVACY_PUBLIC
				TripPrivacyLevel.PRIVATE -> ApiTripListItemResponse.PRIVACY_PRIVATE
				TripPrivacyLevel.SHAREABLE -> ApiTripListItemResponse.PRIVACY_SHAREABLE
			},
			starts_on = DateTimeHelper.timestampToDate(localTrip.startsOn?.timeSeconds),
			destinations = localTrip.destinations,
			days = localTrip.days.map { tripDayConverter.toApi(it) }
		)
	}

	private fun fromApiPrivacyLevel(privacyLevel: String): TripPrivacyLevel {
		return when (privacyLevel) {
			ApiTripListItemResponse.PRIVACY_PUBLIC -> TripPrivacyLevel.PUBLIC
			ApiTripListItemResponse.PRIVACY_PRIVATE -> TripPrivacyLevel.PRIVATE
			ApiTripListItemResponse.PRIVACY_SHAREABLE -> TripPrivacyLevel.SHAREABLE
			else -> TripPrivacyLevel.PRIVATE
		}
	}

	private fun fromApiMedia(media: ApiTripListItemResponse.Media?): TripMedia? {
		return if (media != null) TripMedia(
			squareMediaId = media.square.id,
			squareUrlTemplate = media.square.url_template,
			landscapeMediaId = media.landscape.id,
			landscapeUrlTemplate = media.landscape.url_template,
			portraitId = media.portrait.id,
			portraitUrlTemplate = media.portrait.url_template,
			videoPreviewId = media.video_preview?.id,
			videoPreviewUrlTemplate = media.video_preview?.url_template
		) else null
	}
}
