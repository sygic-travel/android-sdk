package com.sygic.travel.sdk.synchronization.services

import android.content.SharedPreferences
import com.sygic.travel.sdk.common.api.SygicTravelApiClient
import com.sygic.travel.sdk.common.api.checkedExecute
import com.sygic.travel.sdk.synchronization.api.model.ApiChangesResponse
import com.sygic.travel.sdk.synchronization.model.SynchronizationResult
import org.threeten.bp.Instant
import org.threeten.bp.OffsetDateTime
import org.threeten.bp.ZoneOffset
import org.threeten.bp.format.DateTimeFormatter
import java.util.concurrent.locks.ReentrantLock
import kotlin.concurrent.thread

internal class SynchronizationService constructor(
	private val sharedPreferences: SharedPreferences,
	private val apiClient: SygicTravelApiClient,
	private val favoritesSynchronizationService: FavoritesSynchronizationService,
	private val tripsSynchronizationResult: TripsSynchronizationService
) {
	companion object {
		private const val SINCE_KEY = "sync.since"
	}

	var synchronizationCompletionHandler: ((result: SynchronizationResult) -> Unit)? = null
	private val lock = ReentrantLock()

	fun synchronize() {
		if (lock.isLocked) {
			return
		}

		thread(name = "stsdksync") {
			if (!lock.tryLock()) {
				return@thread
			}

			try {
				runSynchronization()
			} finally {
				lock.unlock()
			}
		}
	}

	private fun runSynchronization() {
		val result = SynchronizationResult()
		try {
			synchronizeWithResult(result)
		} catch (e: Exception) {
			result.success = false
			result.exception = e
		} finally {
			synchronizationCompletionHandler?.invoke(result)
		}
	}

	private fun synchronizeWithResult(result: SynchronizationResult) {
		val since = sharedPreferences.getLong(SINCE_KEY, 0)
		val sinceFormatted = Instant.ofEpochMilli(since).atOffset(ZoneOffset.UTC).format(DateTimeFormatter.ISO_OFFSET_DATE_TIME)

		val changesResponse = apiClient.getChanges(sinceFormatted)
			.checkedExecute()
			.body()!!

		val changedTripIds = mutableListOf<String>()
		val deletedTripIds = mutableListOf<String>()
		val addedFavoriteIds = mutableListOf<String>()
		val deletedFavoriteIds = mutableListOf<String>()

		for (change in (changesResponse.data?.changes ?: arrayListOf())) {
			when (change.type) {
				ApiChangesResponse.ChangeEntry.TYPE_TRIP -> {
					if (change.change == ApiChangesResponse.ChangeEntry.CHANGE_UPDATED) {
						changedTripIds.add(change.id!!)
					} else if (change.change == ApiChangesResponse.ChangeEntry.CHANGE_DELETED) {
						deletedTripIds.add(change.id!!)
					}
				}
				ApiChangesResponse.ChangeEntry.TYPE_FAVORITE -> {
					if (change.change == ApiChangesResponse.ChangeEntry.CHANGE_UPDATED) {
						addedFavoriteIds.add(change.id!!)
					} else if (change.change == ApiChangesResponse.ChangeEntry.CHANGE_DELETED) {
						deletedFavoriteIds.add(change.id!!)
					}
				}
				ApiChangesResponse.ChangeEntry.TYPE_SETTINGS -> {
					result.updatedUserSettings = true
				}
				ApiChangesResponse.ChangeEntry.TYPE_CUSTOM_PLACE -> {
					result.changedCustomPlaceIds.add(change.id!!)
				}
				else -> {
					// ignore
				}
			}
		}

		tripsSynchronizationResult.sync(changedTripIds, deletedTripIds, result)
		favoritesSynchronizationService.sync(addedFavoriteIds, deletedFavoriteIds, result)

		val changesFetchedAt = OffsetDateTime.parse(changesResponse.server_timestamp).toInstant().toEpochMilli()
		sharedPreferences.edit()
			.putLong(SINCE_KEY, changesFetchedAt)
			.apply()
	}

	fun clearUserData() {
		sharedPreferences.edit().remove(SINCE_KEY).apply()
	}
}
