package com.sygic.travel.sdk;

import android.content.Context;

import com.sygic.travel.sdk.contentProvider.api.Callback;
import com.sygic.travel.sdk.contentProvider.api.StApi;
import com.sygic.travel.sdk.contentProvider.api.StApiGenerator;
import com.sygic.travel.sdk.contentProvider.api.StObserver;
import com.sygic.travel.sdk.model.api.MediaResponse;
import com.sygic.travel.sdk.model.api.PlaceDetailedResponse;
import com.sygic.travel.sdk.model.api.PlacesResponse;
import com.sygic.travel.sdk.model.media.Medium;
import com.sygic.travel.sdk.model.place.Place;
import com.sygic.travel.sdk.model.query.Query;

import java.io.File;
import java.util.List;

import retrofit2.adapter.rxjava.Result;
import rx.Observable;
import rx.Scheduler;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * <p>Provides public methods for requesting API.</p>
 */
public class StSDK {

	/*-------------------------------------------
	    		       CONSTANTS
	 -------------------------------------------*/

	private static final String TAG = StSDK.class.getSimpleName();

	/*-------------------------------------------
	    		PUBLIC MEMBERS & METHODS
	 -------------------------------------------*/

	public static volatile StSDK instance = null;

	/**
	 * @return Instance of {@link StSDK}.
	 */
	public static StSDK getInstance() {
		if (instance == null) {
			synchronized(StSDK.class) {
				if (instance == null) {
					instance = new StSDK();
				}
			}
		}
		return instance;
	}

	/**
	 * Creates and sends a request to get places, e.g. for map or list.
	 * @param query Query encapsulating data for API request.
	 * @param back Callback. Either {@link Callback#onSuccess(Object)} with places is called, or
	 *             {@link Callback#onFailure(Throwable)} in case of an error is called.
	 */
	public void getPlaces(
		Query query,
		Callback<List<Place>> back
	){
		Observable<Result<PlacesResponse>> unpreparedObservable = getStApi().getPlaces(
			query.getQuery(),
			query.getLevelsQueryString(),
			query.getCategoriesQueryString(),
			query.getMapTilesQueryString(),
			query.getMapSpread(),
			query.getBoundsQueryString(),
			query.getTagsQueryString(),
			query.getParentsQueryString(),
			query.getLimit()
		);
		Observable<Result<PlacesResponse>> preparedObservable = getPreparedObservable(unpreparedObservable);
		subscription = preparedObservable.subscribe(new StObserver<PlacesResponse>(back, false));
	}

	/**
	 * <p>Creates and sends a request to get place with detailed information.</p>
	 * @param id Unique id of a place - detailed information about this place will be requested.
	 * @param back Callback. Either {@link Callback#onSuccess(Object)} with places is called, or
	 *             {@link Callback#onFailure(Throwable)} in case of an error is called.
	 */
	public void getPlaceDetailed(String id, Callback<Place> back){
		Observable<Result<PlaceDetailedResponse>> unpreparedObservable = getStApi().getPlaceDetailed(id);
		Observable<Result<PlaceDetailedResponse>> preparedObservable = getPreparedObservable(unpreparedObservable);
		subscription = preparedObservable.subscribe(new StObserver<PlaceDetailedResponse>(back, false));
	}

	/**
	 * <p>Creates and sends a request to get the place's media.</p>
	 * @param id Unique id of a place - media for this place will be requested.
	 * @param back Callback. Either {@link Callback#onSuccess(Object)} with places is called, or
	 *             {@link Callback#onFailure(Throwable)} in case of an error is called.
	 */
	public void getPlaceMedia(String id, Callback<List<Medium>> back){
		Observable<Result<MediaResponse>> unpreparedObservable = getStApi().getPlaceMedia(id);
		Observable<Result<MediaResponse>> preparedObservable = getPreparedObservable(unpreparedObservable);
		subscription = preparedObservable.subscribe(new StObserver<MediaResponse>(back, false));
	}

	/*-------------------------------------------
	    		          RX
	 -------------------------------------------*/

	/**
	 * <p>Prepares an {@link Observable} - sets {@link Scheduler schedulers}.</p>
	 *
	 * @param unpreparedObservable Observable to be prepared.
	 * @return Observable ready to be subscribed to.
	 */
	public static <T> Observable<T> getPreparedObservable(
		Observable<T> unpreparedObservable
	){
		return unpreparedObservable
			.subscribeOn(Schedulers.newThread())
			.observeOn(AndroidSchedulers.mainThread());
	}

	/**
	 * <p>Unsubscribes a subscribed observable.</p>
	 */
	public void unsubscribeObservable(){
		if(subscription != null && !subscription.isUnsubscribed()){
			subscription.unsubscribe();
		}
	}

	/*-------------------------------------------
	    		PRIVATE MEMBERS & METHODS
	 -------------------------------------------*/

	private StApi stApi;
	private File cacheDir;
	private Subscription subscription;

	private StSDK() {
	}

	/**
	 * <p>Initialization of the SDK.</p>
	 */
	public static void initialize(String xApiKey, Context context) {
		StApiGenerator.headersInterceptor.updateXApiKey(xApiKey);
		StSDK.getInstance().setCacheDir(context.getCacheDir());
	}

	/**
	 * <p>Creates and returns an instance of the {@link StApi} interface.</p>
	 * @return Instance of the {@link StApi} interface.
	 */
	private StApi getStApi() {
		if(stApi == null){
			stApi = StApiGenerator.createStApi(StApi.class, cacheDir);
		}
		return stApi;
	}

	/**
	 * <p>Sets a cache directory.</p>
	 * @param cacheDir Cache directorry.
	 */
	private void setCacheDir(File cacheDir) {
		this.cacheDir = cacheDir;
	}
}
