package com.sygic.travel.sdk.contentProvider.api;

import android.util.Log;

import com.sygic.travel.sdk.model.StResponse;
import com.sygic.travel.sdk.model.place.Place;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import retrofit2.adapter.rxjava.Result;
import rx.Observer;

import static com.sygic.travel.sdk.contentProvider.api.StApi.DETAIL_API_CALL;
import static com.sygic.travel.sdk.contentProvider.api.StApi.MEDIA_API_CALL;
import static com.sygic.travel.sdk.contentProvider.api.StApi.PLACES_API_CALL;


/**
 * This class is for internal usage. It wraps user's callback.
 */
public class StObserver implements Observer<Result<StResponse>> {
	private static final String TAG = StObserver.class.getSimpleName();

	private String requestType;
	private Callback userCallback;

	private StResponse stResponse;
	private List<StResponse> stResponses = new ArrayList<>();

	public StObserver(Callback userCallback, String requestType) {
		this.userCallback = userCallback;
		this.requestType = requestType;
	}

	@Override
	public void onCompleted() {
		switch(requestType){
			case PLACES_API_CALL:
				userCallback.onSuccess(stResponse.getData().getPlaces());
				break;
			case DETAIL_API_CALL:
				userCallback.onSuccess(stResponse.getData().getDetail());
				break;
			case MEDIA_API_CALL:
				userCallback.onSuccess(stResponse.getData().getMedia());
				break;
			default:
				break;
		}
	}

	@Override
	public void onError(Throwable e) {
		userCallback.onFailure(e);
	}

	@Override
	public void onNext(Result<StResponse> stResponseResult) {
		if(stResponseResult.response().errorBody() != null){
			Log.e(TAG, "Error: " + stResponseResult.response().errorBody().toString());
		} else {
//			if(merged){
//				stResponses.add(stResponseResult.response().body());
//			} else {
				stResponse = stResponseResult.response().body();
//			}
		}
	}
}
