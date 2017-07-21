package com.sygic.travel.sdk.provider

import com.sygic.travel.sdk.api.Callback
import com.sygic.travel.sdk.api.StApi
import com.sygic.travel.sdk.api.StObserver
import com.sygic.travel.sdk.api.responseWrappers.MediaResponse
import com.sygic.travel.sdk.api.responseWrappers.PlaceDetailedResponse
import com.sygic.travel.sdk.api.responseWrappers.PlacesResponse
import com.sygic.travel.sdk.api.responseWrappers.TourResponse
import com.sygic.travel.sdk.db.StDb
import com.sygic.travel.sdk.model.media.Medium
import com.sygic.travel.sdk.model.place.Favorite
import com.sygic.travel.sdk.model.place.Place
import com.sygic.travel.sdk.model.place.Tour
import com.sygic.travel.sdk.model.query.PlacesQuery
import com.sygic.travel.sdk.model.query.ToursQuery
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers


internal class DataProvider(
	val stApi: StApi? = null,
	val stDb: StDb? = null
) {
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
	): Disposable {
		val unpreparedObservable = stApi?.getPlaces(
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
		val preparedObservable = getPreparedObservable(unpreparedObservable!!)
		return preparedObservable.subscribeWith(StObserver(callback, false))
	}


	/**
	 *
	 * Creates and sends a request to get place with detailed information.
	 * @param id Unique id of a place - detailed information about this place will be requested.
	 * *
	 * @param back Callback. Either [Callback.onSuccess] with place is called, or
	 * *             [Callback.onFailure] in case of an error is called.
	 */
	fun getPlaceDetailed(id: String, back: Callback<Place?>?): Disposable {
		val unpreparedObservable = stApi?.getPlaceDetailed(id)
		val preparedObservable = getPreparedObservable(unpreparedObservable!!)
		val callback = object : Callback<PlaceDetailedResponse>() {
			override fun onSuccess(data: PlaceDetailedResponse) {
				back?.onSuccess(data.getPlace())
			}

			override fun onFailure(t: Throwable) {
				back?.onFailure(t)
			}
		}
		return preparedObservable.subscribeWith(StObserver(callback, false))
	}

	/**
	 *
	 * Creates and sends a request to get places with detailed information.
	 * @param ids Ids of places - detailed information about these places will be requested.
	 * *
	 * @param back Callback. Either [Callback.onSuccess] with places is called, or
	 * *             [Callback.onFailure] in case of an error is called.
	 */
	fun getPlacesDetailed(ids: List<String>, back: Callback<List<Place>?>?): Disposable {
		val queryIds = ids.joinToString(PlacesQuery.Operator.OR.operator)
		val unpreparedObservable = stApi?.getPlacesDetailed(queryIds)
		val preparedObservable = getPreparedObservable(unpreparedObservable!!)
		val callback = object : Callback<PlacesResponse>() {
			override fun onSuccess(data: PlacesResponse) {
				back?.onSuccess(data.getPlaces())
			}

			override fun onFailure(t: Throwable) {
				back?.onFailure(t)
			}
		}
		return preparedObservable.subscribeWith(StObserver(callback, false))
	}

	/**
	 *
	 * Creates and sends a request to get the place's media.
	 * @param id Unique id of a place - media for this place will be requested.
	 * *
	 * @param back Callback. Either [Callback.onSuccess] with places is called, or
	 * *             [Callback.onFailure] in case of an error is called.
	 */
	fun getPlaceMedia(id: String, back: Callback<List<Medium>?>?): Disposable {
		val unpreparedObservable = stApi?.getPlaceMedia(id)
		val preparedObservable = getPreparedObservable(unpreparedObservable!!)
		val callback = object : Callback<MediaResponse>() {
			override fun onSuccess(data: MediaResponse) {
				back?.onSuccess(data.getMedia())
			}

			override fun onFailure(t: Throwable) {
				back?.onFailure(t)
			}
		}
		return preparedObservable.subscribeWith(StObserver(callback, false))
	}

	/**
	 * Creates and sends a request to get the Tours.
	 * @param toursQuery ToursQuery encapsulating data for API request.
	 *
	 * @param back Callback. Either [Callback.onSuccess] with tours is called, or
	 *            [Callback.onFailure] in case of an error is called.
	 */
	fun getTours(toursQuery: ToursQuery, back: Callback<List<Tour>?>?): Disposable {
		val unpreparedObservable = stApi?.getTours(
				destinationId = toursQuery.destinationId,
				page = toursQuery.page,
				sortBy = toursQuery.sortBy?.string,
				sortDirection = toursQuery.sortDirection?.string
		)
		val preparedObservable = getPreparedObservable(unpreparedObservable!!)
		val callback = object : Callback<TourResponse>() {
			override fun onSuccess(data: TourResponse) {
				back?.onSuccess(data.getTours())
			}

			override fun onFailure(t: Throwable) {
				back?.onFailure(t)
			}
		}
		return preparedObservable.subscribeWith(StObserver(callback, false))
	}


	fun addPlaceToFavorites(id: String, back: Callback<String>?) {
		Thread(Runnable {
			val insertedCount = stDb?.favoriteDao()?.insert(Favorite(id))
			if (insertedCount != null && insertedCount > 0L) {
				back?.onSuccess("Success")
			} else {
				back?.onFailure(Exception("Favorite not added!"))
			}

		}).start()
	}


	fun removePlaceFromFavorites(id: String, back: Callback<String>?) {
		Thread(Runnable {
			val insertedCount = stDb?.favoriteDao()?.delete(Favorite(id))
			if (insertedCount == 1) {
				back?.onSuccess("Success")
			} else {
				back?.onFailure(Exception("Favorite not removed!"))
			}
		}).start()
	}


	fun getFavoritesIds(back: Callback<List<String>?>?) {
		Thread(Runnable {
			val favorites = stDb?.favoriteDao()?.loadAll()
			val favoritesIds: MutableList<String> = mutableListOf()
			favorites?.mapTo(favoritesIds) {
				it.id!!
			}

			if (favoritesIds.size > 0) {
				back?.onSuccess(favoritesIds)
			} else {
				back?.onFailure(Exception("No favorites loaded from DB!"))
			}
		}).start()
	}
	
	
	/**
	 *
	 * Prepares an [Observable] - sets [schedulers][Scheduler].
	 * @param unpreparedObservable Observable to be prepared.
	 * *
	 * @return Observable ready to be subscribed to.
	 */
	fun <T> getPreparedObservable(
			unpreparedObservable: Observable<T>
	): Observable<T> {
		return unpreparedObservable
				.subscribeOn(Schedulers.newThread())
				.observeOn(AndroidSchedulers.mainThread())
	}
}