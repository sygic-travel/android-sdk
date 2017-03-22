package com.sygic.travel.sdk;

import android.content.Context;

import com.sygic.travel.sdk.contentProvider.api.StCallback;
import com.sygic.travel.sdk.contentProvider.api.StApiGenerator;
import com.sygic.travel.sdk.contentProvider.api.StApi;
import com.sygic.travel.sdk.contentProvider.api.Callback;
import com.sygic.travel.sdk.model.StResponse;
import com.sygic.travel.sdk.model.media.Media;
import com.sygic.travel.sdk.model.place.Place;
import com.sygic.travel.sdk.model.query.Query;

import java.io.File;
import java.util.List;

import retrofit2.Call;

import static com.sygic.travel.sdk.contentProvider.api.StApi.*;

/**
 * This class provides public methods for user, it is starting point of app and it initialize
 * all inner ?structures?.
 */
public class StSDK {

	/********************************************
	 *  		PUBLIC MEMBERS & METHODS
	 ********************************************/

	public static volatile StSDK instance = null;

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

	public void getPlaces(
		Query query,
		Callback<List<Place>> back
	){
		Call<StResponse> call = getStApi().getPlaces(
			query.getQuery(),
			query.getLevel(),
			query.getCategories(),
			query.getMapTile(),
			query.getMapSpread(),
			query.getBounds(),
			query.getTags(),
			query.getParent(),
			query.getLimit()
		);

		StCallback<StResponse> stCallback = new StCallback<>(back, PLACES_API_CALL);
		call.enqueue(stCallback);
	}

	public void getPlaceDetailed(String guid, Callback<Place> back){
		Call<StResponse> call = getStApi().getPlaceDetailed(guid);
		StCallback<StResponse> stCallback = new StCallback<>(back, DETAIL_API_CALL);
		call.enqueue(stCallback);
	}


	public void getPlaceMedia(String guid, Callback<Media> back){
		Call<StResponse> call = getStApi().getPlaceDetailed(guid);
		StCallback<StResponse> stCallback = new StCallback<>(back, MEDIA_API_CALL);
		call.enqueue(stCallback);
	}
	/********************************************
	 *  		PRIVATE MEMBERS & METHODS
	 ********************************************/

	private StApi stApi;
	private File cacheDir;

	private StSDK() {
	}

	/**
	 * This method should initialize all necessary objects
	 */
	public static void initialize(String xApiKey, Context context) {
		StApiGenerator.authorizationInterceptor.updateXApiKey(xApiKey);
		StSDK.getInstance().setCacheDir(context.getCacheDir());
	}

	/**
	 * Example of usage:
	 * Call<List<Place>> call = stApi.getPlaces(...);
	 * call.enque(callback)
	 *
	 * @return instance of StApi class
	 */
	private StApi getStApi() {
		if(stApi == null){
			stApi = StApiGenerator.createStApi(StApi.class, cacheDir);
		}
		return stApi;
	}

	private void setCacheDir(File cacheDir) {
		this.cacheDir = cacheDir;
	}
}
