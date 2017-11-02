package com.sygic.travel.sdk.trips.api

import com.sygic.travel.sdk.trips.api.model.ApiTripItemRequest
import com.sygic.travel.sdk.trips.api.model.ApiTripItemResponse
import com.sygic.travel.sdk.trips.api.model.ApiTripListItemResponse
import com.sygic.travel.sdk.trips.model.Trip
import com.sygic.travel.sdk.trips.model.TripDay
import com.sygic.travel.sdk.trips.model.TripInfo
import com.sygic.travel.sdk.trips.model.TripMedia
import com.sygic.travel.sdk.utils.DateTimeHelper

class TripConverter constructor(
	private val tripDayConverter: TripDayConverter
) {
	fun fromApi(localTrip: TripInfo, apiTrip: ApiTripListItemResponse) {
		localTrip.ownerId = apiTrip.owner_id
		localTrip.name = apiTrip.name
		localTrip.version = apiTrip.version
		localTrip.url = apiTrip.url
		localTrip.updatedAt = DateTimeHelper.datetimeToTimestamp(apiTrip.updated_at)!!
		localTrip.isDeleted = apiTrip.is_deleted
		localTrip.privacyLevel = when (apiTrip.privacy_level) {
			ApiTripListItemResponse.PRIVACY_PUBLIC -> TripInfo.PrivacyLevel.PUBLIC
			ApiTripListItemResponse.PRIVACY_PRIVATE -> TripInfo.PrivacyLevel.PRIVATE
			ApiTripListItemResponse.PRIVACY_SHAREABLE -> TripInfo.PrivacyLevel.SHAREABLE
			else -> TripInfo.PrivacyLevel.PRIVATE
		}
		localTrip.privileges = TripInfo.Privileges(
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
	}

	fun fromApi(localTrip: Trip, apiTrip: ApiTripItemResponse) {
		fromApi(localTrip, apiTrip as ApiTripListItemResponse)
		localTrip.destinations = ArrayList(apiTrip.destinations)
		val daysMaxIndex = localTrip.days.size - 1
		for ((i, day) in apiTrip.days.iterator().withIndex()) {
			val tripDay: TripDay
			if (daysMaxIndex < i) {
				tripDay = TripDay()
				tripDay.dayIndex = i
				tripDay.tripId = localTrip.id
				localTrip.days.add(tripDay)
			} else {
				tripDay = localTrip.days[i]
			}
			tripDayConverter.fromApi(tripDay, day)
		}

//		for (index in apiTrip.days.size..(daysMaxIndex + 1)) {
//			localTrip.days!!.removeAt(index)
//		}

		localTrip.reindexDays()
		localTrip.isChanged = false
	}

	fun toApi(localTrip: Trip): ApiTripItemRequest {
		return ApiTripItemRequest(
			name = localTrip.name,
			base_version = localTrip.version,
			updated_at = DateTimeHelper.timestampToDatetime(localTrip.updatedAt)!!,
			is_deleted = localTrip.isDeleted,
			privacy_level = when (localTrip.privacyLevel) {
				TripInfo.PrivacyLevel.PUBLIC -> ApiTripListItemResponse.PRIVACY_PUBLIC
				TripInfo.PrivacyLevel.PRIVATE -> ApiTripListItemResponse.PRIVACY_PRIVATE
				TripInfo.PrivacyLevel.SHAREABLE -> ApiTripListItemResponse.PRIVACY_SHAREABLE
			},
			starts_on = DateTimeHelper.timestampToDatetime(localTrip.startsOn),
			ends_on = null,
			destinations = localTrip.destinations,
			days = localTrip.days.map { tripDayConverter.toApi(it) }
		)
	}
}
