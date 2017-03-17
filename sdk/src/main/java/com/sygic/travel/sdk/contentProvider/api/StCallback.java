package com.sygic.travel.sdk.contentProvider.api;

import com.sygic.travel.sdk.model.StResponse;

import retrofit2.Call;
import retrofit2.Response;

import static com.sygic.travel.sdk.contentProvider.api.StApi.DETAIL_API_CALL;
import static com.sygic.travel.sdk.contentProvider.api.StApi.MEDIA_API_CALL;
import static com.sygic.travel.sdk.contentProvider.api.StApi.PLACES_API_CALL;


/**
 * This class is for internal usage. It wraps user's callback.
 * @param <T>
 */
public class StCallback<T> implements retrofit2.Callback<T> {

	private String requestType;
	private Callback userCallback;

	public StCallback(Callback userCallback, String requestType) {
		this.userCallback = userCallback;
		this.requestType = requestType;

	}

	@Override
	public void onResponse(Call<T> call, Response<T> response) {
		Object ret;
		StResponse stResponse = (StResponse) response.body();
		switch(requestType){
			case PLACES_API_CALL:
				ret = stResponse.getData().getPlaces();
				break;
			case DETAIL_API_CALL:
				ret = stResponse.getData().getPlace();
				break;
			case MEDIA_API_CALL:
				ret = stResponse.getData().getMedia();
				break;
			default:
				ret = null;
				break;
		}
		userCallback.onSuccess(ret);
	}

	@Override
	public void onFailure(Call<T> call, Throwable t) {
		userCallback.onFailure(t);
	}
}
