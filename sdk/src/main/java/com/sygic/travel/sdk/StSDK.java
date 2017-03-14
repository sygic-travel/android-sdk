package com.sygic.travel.sdk;

import com.sygic.travel.sdk.contentProvider.api.ServiceGenerator;
import com.sygic.travel.sdk.contentProvider.api.StApi;
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
	private File cacheDir; //TODO

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

	//TODO replace for Query class which was create in defferent branch
	public void getPlaces(
		Query query,
		Callback<List<Place>> back
	){
		Call<List<Place>> call = stApi.getPlaces(
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
	/********************************************
	 *  		PRIVATE MEMBERS & METHODS
	 ********************************************/

	private StApi stApi;
	
	private StSDK() {
		initialize();
	}

	/**
	 * This method should initialize all necessary objects
	 */
	private void initialize() {
		getStApi();
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
			stApi = ServiceGenerator.createService(StApi.class);
		}
		return stApi;
	}

	public File getCacheDir() {
		return cacheDir;
	}
}
