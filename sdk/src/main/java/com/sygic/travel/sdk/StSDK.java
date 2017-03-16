package com.sygic.travel.sdk;

import android.content.Context;

import com.sygic.travel.sdk.contentProvider.api.ServiceGenerator;
import com.sygic.travel.sdk.contentProvider.api.StApi;
import com.sygic.travel.sdk.model.place.Detail;
import com.sygic.travel.sdk.model.place.Place;
import com.sygic.travel.sdk.model.query.Query;

import java.io.File;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

/**
 * This class provides public methods for user. It is starting point of app aswell and it initialize
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
		Call<List<Place>> call = getStApi().getPlaces(
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
		call.enqueue(back);
	}

	public Detail/* TODO ?Place?*/ getPlaceDetailed(String guid){
		//TODO
		return null;
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
		ServiceGenerator.authorizationInterceptor.updateXApiKey(xApiKey);
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
			stApi = ServiceGenerator.createService(StApi.class, cacheDir);
		}
		return stApi;
	}

	private void setCacheDir(File cacheDir) {
		this.cacheDir = cacheDir;
	}
}
