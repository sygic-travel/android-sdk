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

@Suppress("DEPRECATION")
internal class TripConverter constructor(
	private val tripDayConverter: TripDayConverter
) {
	fun fromApi(apiTrip: ApiTripListItemResponse): TripInfo {
		val localTrip = Trip(apiTrip.id)
		localTrip.ownerId = apiTrip.owner_id
		localTrip.name = apiTrip.name
		localTrip.version = apiTrip.version
		localTrip.url = apiTrip.url
		localTrip.updatedAt = DateTimeHelper.datetimeToTimestamp(apiTrip.updated_at)!!
		localTrip.isDeleted = apiTrip.is_deleted
		localTrip.privacyLevel = when (apiTrip.privacy_level) {
			ApiTripListItemResponse.PRIVACY_PUBLIC -> TripPrivacyLevel.PUBLIC
			ApiTripListItemResponse.PRIVACY_PRIVATE -> TripPrivacyLevel.PRIVATE
			ApiTripListItemResponse.PRIVACY_SHAREABLE -> TripPrivacyLevel.SHAREABLE
			else -> TripPrivacyLevel.PRIVATE
		}
		localTrip.privileges = TripPrivileges(
			edit = apiTrip.privileges.edit,
			manage = apiTrip.privileges.manage,
			delete = apiTrip.privileges.delete
		)
		localTrip.startsOn = DateTimeHelper.dateToTimestamp(apiTrip.starts_on)
		localTrip.daysCount = apiTrip.days_count
		localTrip.media = if (apiTrip.media != null) TripMedia(
			squareMediaId = apiTrip.media.square.id,
			squareUrlTemplate = apiTrip.media.square.url_template,
			landscapeMediaId = apiTrip.media.landscape.id,
			landscapeUrlTemplate = apiTrip.media.landscape.url_template,
			portraitId = apiTrip.media.portrait.id,
			portraitUrlTemplate = apiTrip.media.portrait.url_template,
			videoPreviewId = apiTrip.media.video_preview?.id,
			videoPreviewUrlTemplate = apiTrip.media.video_preview?.url_template
		) else null
		return localTrip
	}

	fun fromApi(apiTrip: ApiTripItemResponse): Trip {
		val localTrip = fromApi(apiTrip as ApiTripListItemResponse) as Trip
		localTrip.destinations = ArrayList(apiTrip.destinations)
		localTrip.days = apiTrip.days
			.map { tripDayConverter.fromApi(it, localTrip) }
			.toMutableList()
		return localTrip
	}

	fun toApi(localTrip: Trip): ApiTripItemRequest {
		return ApiTripItemRequest(
			name = localTrip.name,
			base_version = localTrip.version,
			updated_at = DateTimeHelper.timestampToDatetime(localTrip.updatedAt)!!,
			is_deleted = localTrip.isDeleted,
			privacy_level = when (localTrip.privacyLevel) {
				TripPrivacyLevel.PUBLIC -> ApiTripListItemResponse.PRIVACY_PUBLIC
				TripPrivacyLevel.PRIVATE -> ApiTripListItemResponse.PRIVACY_PRIVATE
				TripPrivacyLevel.SHAREABLE -> ApiTripListItemResponse.PRIVACY_SHAREABLE
			},
			starts_on = DateTimeHelper.timestampToDate(localTrip.startsOn),
			destinations = localTrip.destinations,
			days = localTrip.days.map { tripDayConverter.toApi(it) }
		)
	}
}
