package com.sygic.travel.sdk

import android.content.Context
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.os.Build
import com.sygic.travel.sdk.api.responseWrappers.MediaResponse
import com.sygic.travel.sdk.api.responseWrappers.PlaceDetailedResponse
import com.sygic.travel.sdk.api.responseWrappers.PlacesResponse
import com.sygic.travel.sdk.api.responseWrappers.TourResponse
import com.sygic.travel.sdk.api.Callback
import com.sygic.travel.sdk.api.StApi
import com.sygic.travel.sdk.api.StApiGenerator
import com.sygic.travel.sdk.api.StObserver
import com.sygic.travel.sdk.model.media.Medium
import com.sygic.travel.sdk.model.place.Place

import com.sygic.travel.sdk.model.place.Tour
import com.sygic.travel.sdk.model.query.PlacesQuery
import com.sygic.travel.sdk.model.query.ToursQuery
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.disposables.Disposables
import io.reactivex.schedulers.Schedulers

import java.io.File

/**
 *
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
    fun getPlaces(
            placesQuery: PlacesQuery,
            back: Callback<List<Place>?>?
    ) {
        val unpreparedObservable = getStApi().getPlaces(
                placesQuery.query,
                placesQuery.levelsQueryString,
                placesQuery.categoriesQueryString,
                placesQuery.mapTilesQueryString,
                placesQuery.mapSpread,
                placesQuery.boundsQueryString,
                placesQuery.tagsQueryString,
                placesQuery.parentsQueryString,
                placesQuery.limit
        )
        val callback = object : Callback<PlacesResponse>() {
            override fun onSuccess(data: PlacesResponse) {
                back?.onSuccess(data.getPlaces())
            }

            override fun onFailure(t: Throwable) {
                back?.onFailure(t)
            }
        }
        val preparedObservable = getPreparedObservable(unpreparedObservable)
        disposable = preparedObservable.subscribeWith(StObserver(callback, false))
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
        val unpreparedObservable = getStApi().getPlaceDetailed(id)
        val preparedObservable = getPreparedObservable(unpreparedObservable)
        val callback = object : Callback<PlaceDetailedResponse>() {
            override fun onSuccess(data: PlaceDetailedResponse) {
                back.onSuccess(data.getPlace())
            }

            override fun onFailure(t: Throwable) {
                back.onFailure(t)
            }
        }
        disposable = preparedObservable.subscribeWith(StObserver(callback, false))
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
        val queryIds = ids.joinToString(PlacesQuery.Operator.OR.operator)
        val unpreparedObservable = getStApi().getPlacesDetailed(queryIds)
        val preparedObservable = getPreparedObservable(unpreparedObservable)
        val callback = object : Callback<PlacesResponse>() {
            override fun onSuccess(data: PlacesResponse) {
                back.onSuccess(data.getPlaces())
            }

            override fun onFailure(t: Throwable) {
                back.onFailure(t)
            }
        }
        disposable = preparedObservable.subscribeWith(StObserver(callback, false))
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
        val unpreparedObservable = getStApi().getPlaceMedia(id)
        val preparedObservable = getPreparedObservable(unpreparedObservable)
        val callback = object : Callback<MediaResponse>() {
            override fun onSuccess(data: MediaResponse) {
                back.onSuccess(data.getMedia())
            }

            override fun onFailure(t: Throwable) {
                back.onFailure(t)
            }
        }
        disposable = preparedObservable.subscribeWith(StObserver(callback, false))
    }

    /**
     * Creates and sends a request to get the Tours.
     * @param toursQuery ToursQuery encapsulating data for API request.
     *
     * @param back Callback. Either [Callback.onSuccess] with tours is called, or
     *            [Callback.onFailure] in case of an error is called.
     */
    fun getTours(toursQuery: ToursQuery, back: Callback<List<Tour>?>?) {
        val unpreparedObservable = getStApi().getTours(
                destinationId = toursQuery.destinationId,
                page = toursQuery.page,
                sortBy = toursQuery.sortBy?.string,
                sortDirection = toursQuery.sortDirection?.string
        )
        val preparedObservable = getPreparedObservable(unpreparedObservable)
        val callback = object : Callback<TourResponse>() {
            override fun onSuccess(data: TourResponse) {
                back?.onSuccess(data.getTours())
            }

            override fun onFailure(t: Throwable) {
                back?.onFailure(t)
            }
        }
        disposable = preparedObservable.subscribeWith(StObserver(callback, false))
    }

    /**
     *
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

    private var stApi: StApi? = null
    private var cacheDir: File? = null
    private var disposable : Disposable = Disposables.empty()

    /**
     *
     * Creates and returns an _instance of the [StApi] interface.
     * @return Instance of the [StApi] interface.
     */
    private fun getStApi(): StApi {
        if (stApi == null) {
            assert(cacheDir != null)
            stApi = StApiGenerator.createStApi(StApi::class.java, cacheDir as File)
        }
        return stApi as StApi
    }

    /**
     *
     * Sets a cache directory.
     * @param cacheDir Cache directorry.
     */
    internal fun setCacheDir(cacheDir: File) {
        this.cacheDir = cacheDir
    }


    /**
     *
     * Prepares an [Observable] - sets [schedulers][Scheduler].
     * @param unpreparedObservable Observable to be prepared.
     * *
     * @return Disposable ready to be subscribed to.
     */
    fun <T> getPreparedObservable(unpreparedObservable: Observable<T>): Observable<T> {
        return unpreparedObservable
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
    }


    companion object {
        private val ST_SDK_NAME_AND_VERSION = "sygic-travel-sdk-android/sdk-version"
        private val ANDROID_VERSION = "Android/" + Build.VERSION.RELEASE

        private var _instance: StSDK? = null


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
         */
        fun initialize(xApiKey: String, context: Context) {
            StApiGenerator.headersInterceptor.setApiKey(xApiKey)
            StApiGenerator.headersInterceptor.setUserAgent(createUserAgent(context))
            getInstance().setCacheDir(context.cacheDir)
        }

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
    }
}
