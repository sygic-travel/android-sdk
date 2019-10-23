package com.sygic.travel.sdk.trips.api

import com.sygic.travel.sdk.trips.api.model.ApiTripItemRequest
import com.sygic.travel.sdk.trips.api.model.ApiTripItemResponse
import com.sygic.travel.sdk.trips.api.model.ApiTripListItemResponse
import com.sygic.travel.sdk.trips.model.Trip
import com.sygic.travel.sdk.trips.model.TripBase
import com.sygic.travel.sdk.trips.model.TripMedia
import com.sygic.travel.sdk.trips.model.TripPrivacyLevel
import com.sygic.travel.sdk.trips.model.TripPrivileges
import org.threeten.bp.LocalDate
import org.threeten.bp.OffsetDateTime
import org.threeten.bp.ZoneOffset
import org.threeten.bp.format.DateTimeFormatter

@Suppress("DEPRECATION")
internal class TripConverter constructor(
	private val tripDayConverter: TripDayConverter
) {
	fun fromApi(apiTrip: ApiTripListItemResponse): TripBase {
		return TripBase(
			id = apiTrip.id,
			name = apiTrip.name,
			startsOn = apiTrip.starts_on?.let { LocalDate.parse(it) },
			privacyLevel = fromApiPrivacyLevel(apiTrip.privacy_level),
			url = apiTrip.url,
			privileges = TripPrivileges(
				edit = apiTrip.privileges.edit,
				manage = apiTrip.privileges.manage,
				delete = apiTrip.privileges.delete
			),
			isUserSubscribed = apiTrip.user_is_subscribed,
			isDeleted = apiTrip.is_deleted,
			media = fromApiMedia(apiTrip.media),
			updatedAt = OffsetDateTime.parse(apiTrip.updated_at).toInstant(),
			isChanged = false,
			ownerId = apiTrip.owner_id,
			version = apiTrip.version,
			daysCount = apiTrip.day_count
		)
	}

	fun fromApi(apiTrip: ApiTripItemResponse): Trip {
		return Trip(
			id = apiTrip.id,
			name = apiTrip.name,
			startsOn = apiTrip.starts_on?.let { LocalDate.parse(it) },
			privacyLevel = fromApiPrivacyLevel(apiTrip.privacy_level),
			url = apiTrip.url,
			privileges = TripPrivileges(
				edit = apiTrip.privileges.edit,
				manage = apiTrip.privileges.manage,
				delete = apiTrip.privileges.delete
			),
			isUserSubscribed = apiTrip.user_is_subscribed,
			isDeleted = apiTrip.is_deleted,
			media = fromApiMedia(apiTrip.media),
			updatedAt = OffsetDateTime.parse(apiTrip.updated_at).toInstant(),
			isChanged = false,
			ownerId = apiTrip.owner_id,
			version = apiTrip.version,
			destinations = ArrayList(apiTrip.destinations),
			days = apiTrip.days.map { tripDayConverter.fromApi(it) }
		)
	}

	fun toApi(localTrip: Trip): ApiTripItemRequest {
		return ApiTripItemRequest(
			name = localTrip.name,
			base_version = localTrip.version,
			updated_at = localTrip.updatedAt!!.atOffset(ZoneOffset.UTC).format(DateTimeFormatter.ISO_OFFSET_DATE_TIME),
			is_deleted = localTrip.isDeleted,
			privacy_level = when (localTrip.privacyLevel) {
				TripPrivacyLevel.PUBLIC -> ApiTripListItemResponse.PRIVACY_PUBLIC
				TripPrivacyLevel.PRIVATE -> ApiTripListItemResponse.PRIVACY_PRIVATE
				TripPrivacyLevel.SHAREABLE -> ApiTripListItemResponse.PRIVACY_SHAREABLE
			},
			starts_on = localTrip.startsOn?.format(DateTimeFormatter.ISO_LOCAL_DATE),
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
