package com.sygic.travel.sdk

import android.arch.persistence.room.Room
import android.content.Context
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.os.Build
import com.sygic.travel.sdk.api.Callback
import com.sygic.travel.sdk.api.StApi
import com.sygic.travel.sdk.api.StApiGenerator
import com.sygic.travel.sdk.db.StDb
import com.sygic.travel.sdk.model.media.Medium
import com.sygic.travel.sdk.model.place.Place
import com.sygic.travel.sdk.model.place.Tour
import com.sygic.travel.sdk.model.query.PlacesQuery
import com.sygic.travel.sdk.model.query.ToursQuery
import com.sygic.travel.sdk.provider.DataProvider
import io.reactivex.disposables.Disposable
import io.reactivex.disposables.Disposables
import java.io.File

/**
 * Provides public methods for requesting API.
 */
class StSDK internal constructor() {

	/**
	 * Creates and sends a request to get places, e.g. for map or list.
	 * @param placesQuery PlacesQuery encapsulating data for API request.
	 * *
	 * @param back Callback. Either [Callback.onSuccess] with places is called, or
	 * *             [Callback.onFailure] in case of an error is called.
	 */
	fun getPlaces(placesQuery: PlacesQuery, back: Callback<List<Place>?>?) {
		disposable = dataProvider?.getPlaces(placesQuery, back)!!
	}


	/**
	 *
	 * Creates and sends a request to get place with detailed information.
	 * @param id Unique id of a place - detailed information about this place will be requested.
	 * *
	 * @param back Callback. Either [Callback.onSuccess] with place is called, or
	 * *             [Callback.onFailure] in case of an error is called.
	 */
	fun getPlaceDetailed(id: String, back: Callback<Place?>) {
		disposable = dataProvider?.getPlaceDetailed(id, back)!!
	}


	/**
	 *
	 * Creates and sends a request to get places with detailed information.
	 * @param ids Ids of places - detailed information about these places will be requested.
	 * *
	 * @param back Callback. Either [Callback.onSuccess] with places is called, or
	 * *             [Callback.onFailure] in case of an error is called.
	 */
	fun getPlacesDetailed(ids: List<String>, back: Callback<List<Place>?>) {
		disposable = dataProvider?.getPlacesDetailed(ids, back)!!
	}


	/**
	 *
	 * Creates and sends a request to get the place's media.
	 * @param id Unique id of a place - media for this place will be requested.
	 * *
	 * @param back Callback. Either [Callback.onSuccess] with places is called, or
	 * *             [Callback.onFailure] in case of an error is called.
	 */
	fun getPlaceMedia(id: String, back: Callback<List<Medium>?>) {
		disposable = dataProvider?.getPlaceMedia(id, back)!!
	}


	/**
	 * Creates and sends a request to get the Tours.
	 * @param toursQuery ToursQuery encapsulating data for API request.
	 *
	 * @param back Callback. Either [Callback.onSuccess] with tours is called, or
	 *            [Callback.onFailure] in case of an error is called.
	 */
	fun getTours(toursQuery: ToursQuery, back: Callback<List<Tour>?>?) {
		disposable = dataProvider?.getTours(toursQuery, back)!!
	}

	/**
	 * Stores a place's id in a local persistent storage. The place is added to the favorites.
	 * @param id A place's id, which is stored.
	 *
	 * @param back Callback. Either [Callback.onSuccess] with tours is called, or
	 *            [Callback.onFailure] in case of an error is called.
	 */
	fun addPlaceToFavorites(id: String, back: Callback<String>?) {
		dataProvider?.addPlaceToFavorites(id, back)
	}


	/**
	 * Removes a place's id from a local persistent storage. The place is removed from the favorites.
	 * @param id A place's id, which is removed.
	 *
	 * @param back Callback. Either [Callback.onSuccess] with tours is called, or
	 *            [Callback.onFailure] in case of an error is called.
	 */
	fun removePlaceFromFavorites(id: String, back: Callback<String>?) {
		dataProvider?.removePlaceFromFavorites(id, back)
	}

	/**
	 * Method returns a list of all favorite places' ids.
	 * @param back Callback. Either [Callback.onSuccess] with tours is called, or
	 *            [Callback.onFailure] in case of an error is called.
	 */
	fun getFavoritesIds(back: Callback<List<String>?>?) {
		dataProvider?.getFavoritesIds(back)
	}


	/**
	 * Unsubscribes a subscribed observable.
	 */
	fun unsubscribeObservable() {
		if (!disposable.isDisposed) {
			disposable.dispose()
		}
	}

	/*-------------------------------------------
				PRIVATE MEMBERS & METHODS
	 -------------------------------------------*/
	private val ST_SDK_NAME_AND_VERSION = "sygic-travel-sdk-android/sdk-version"
	private val ANDROID_VERSION = "Android/" + Build.VERSION.RELEASE
	private val DATABASE_NAME = "st-sdk-db"

	private var dataProvider: DataProvider? = null
	private var stApi: StApi? = null
	private var stDb: StDb? = null
	private var disposable: Disposable = Disposables.empty()


	/**
	 * @return UserAgent to be sent as a header in every request.
	 */
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


	/**
	 * Creates an instance of [DataProvider].
	 * @param context Application's context.
	 */
	private fun intializeDataProvider(context: Context) {
		if (dataProvider == null) {
			dataProvider = DataProvider(getStApi(context.cacheDir), getStDb(context))
		}
	}


	/**
	 * Creates and returns an instance of the [StApi] interface.
	 * @return Instance of the [StApi] interface.
	 */
	private fun getStApi(cacheDir: File?): StApi {
		if (stApi == null) {
			assert(cacheDir != null)
			stApi = StApiGenerator.createStApi(StApi::class.java, cacheDir as File)
		}
		return stApi as StApi
	}

	/**
	 * Creates and returns an instance of [StDb].
	 * @return Instance of the [StDb] interface.
	 */
	private fun getStDb(context: Context): StDb {
		if (stDb == null) {
			stDb = Room.databaseBuilder(
				context,
				StDb::class.java,
				DATABASE_NAME
			).build()
		}
		return stDb as StDb
	}


	companion object {
		private var _instance: StSDK? = null


		/**
		 * Creates and returns an instance of [StSDK].
		 * @return SDK instance
		 */
		fun getInstance(): StSDK {
			if (_instance == null) {
				synchronized(StSDK::class.java) {
					if (_instance == null) {
						_instance = StSDK()
					}
				}
			}
			return _instance!!
		}


		/**
		 * Initialization of the SDK.
		 * @param xApiKey Api key - must be provided.
		 * @param context Application's context.
		 */
		fun initialize(xApiKey: String, context: Context) {
			StApiGenerator.headersInterceptor.setApiKey(xApiKey)
			StApiGenerator.headersInterceptor.setUserAgent(getInstance().createUserAgent(context))

			getInstance().intializeDataProvider(context)
		}
	}
}
