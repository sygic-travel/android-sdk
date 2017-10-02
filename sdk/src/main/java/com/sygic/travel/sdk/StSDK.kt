package com.sygic.travel.sdk

import android.arch.persistence.room.Room
import android.content.Context
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.os.Build
import com.sygic.travel.sdk.api.StApi
import com.sygic.travel.sdk.api.StApiGenerator
import com.sygic.travel.sdk.db.StDb
import com.sygic.travel.sdk.model.media.Medium
import com.sygic.travel.sdk.model.place.Place
import com.sygic.travel.sdk.model.place.Tour
import com.sygic.travel.sdk.model.query.PlacesQuery
import com.sygic.travel.sdk.model.query.ToursQuery
import com.sygic.travel.sdk.provider.DataProvider
import com.sygic.travel.sdk.utils.runWithCallback
import java.io.File

/**
 * Provides public methods for requesting API.
 */
class StSDK internal constructor(context: Context, cacheDir: File) {
	companion object {
		private val ST_SDK_NAME_AND_VERSION = "sygic-travel-sdk-android/sdk-version"
		private val ANDROID_VERSION = "Android/" + Build.VERSION.RELEASE
		private val DATABASE_NAME = "st-sdk-db"

		/**
		 * Creates the SDK.
		 * @param xApiKey Api key - must be provided.
		 * @param context Application's context.
		 */
		fun create(xApiKey: String, context: Context): StSDK {
			StApiGenerator.headersInterceptor.setApiKey(xApiKey)
			StApiGenerator.headersInterceptor.setUserAgent(createUserAgent(context))
			return StSDK(context, context.cacheDir)
		}

		private fun createUserAgent(context: Context): String {
			var packageInfo: PackageInfo? = null
			try {
				packageInfo = context.packageManager.getPackageInfo(context.packageName, 0)
			} catch (e: PackageManager.NameNotFoundException) {
				e.printStackTrace()
			}

			var userAgent = ""
			userAgent += context.packageName + "/"
			userAgent += if (packageInfo != null) packageInfo.versionName + " " else ""
			userAgent += ST_SDK_NAME_AND_VERSION + " "
			userAgent += ANDROID_VERSION
			return userAgent
		}
	}


	private var stApi: StApi = StApiGenerator.createStApi(StApi::class.java, cacheDir)
	private var stDb: StDb = Room.databaseBuilder(context, StDb::class.java, DATABASE_NAME).build()
	private var dataProvider: DataProvider = DataProvider(stApi, stDb)


	/**
	 * Creates and sends a request to get places, e.g. for map or list.
	 * @param placesQuery PlacesQuery encapsulating data for API request.
	 */
	fun getPlaces(placesQuery: PlacesQuery, callback: Callback<List<Place>?>) {
		runWithCallback({ dataProvider.getPlaces(placesQuery) }, callback)
	}


	/**
	 * Creates and sends a request to get place with detailed information.
	 * @param id Unique id of a place - detailed information about this place will be requested.
	 */
	fun getPlaceDetailed(id: String, callback: Callback<Place?>) {
		runWithCallback({ dataProvider.getPlaceDetailed(id) }, callback)
	}


	/**
	 * Creates and sends a request to get places with detailed information.
	 * @param ids Ids of places - detailed information about these places will be requested.
	 */
	fun getPlacesDetailed(ids: List<String>, callback: Callback<List<Place>?>) {
		runWithCallback({ dataProvider.getPlacesDetailed(ids) }, callback)
	}


	/**
	 * Creates and sends a request to get the place's media.
	 * @param id Unique id of a place - media for this place will be requested.
	 */
	fun getPlaceMedia(id: String, callback: Callback<List<Medium>?>) {
		runWithCallback({ dataProvider.getPlaceMedia(id) }, callback)
	}


	/**
	 * Creates and sends a request to get the Tours.
	 * @param toursQuery ToursQuery encapsulating data for API request.
	 */
	fun getTours(toursQuery: ToursQuery, callback: Callback<List<Tour>?>) {
		runWithCallback({ dataProvider.getTours(toursQuery) }, callback)
	}


	/**
	 * Stores a place's id in a local persistent storage. The place is added to the favorites.
	 * @param id A place's id, which is stored.
	 */
	fun addPlaceToFavorites(id: String, callback: Callback<Unit>) {
		runWithCallback({ dataProvider.addPlaceToFavorites(id) }, callback)
	}


	/**
	 * Removes a place's id from a local persistent storage. The place is removed from the favorites.
	 * @param id A place's id, which is removed.
	 */
	fun removePlaceFromFavorites(id: String, callback: Callback<Unit>) {
		runWithCallback({ dataProvider.removePlaceFromFavorites(id) }, callback)
	}


	/**
	 * Method returns a list of all favorite places' ids.
	 */
	fun getFavoritesIds(callback: Callback<List<String>?>) {
		runWithCallback({ dataProvider.getFavoritesIds() }, callback)
	}
}
