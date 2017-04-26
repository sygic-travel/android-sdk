package com.sygic.travel.sdk.contentProvider.api;

import android.util.Log;

import com.sygic.travel.sdk.model.StResponse;
import com.sygic.travel.sdk.model.place.Place;

import java.util.ArrayList;
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
	private boolean multipleCallsMerged;

	private StResponse stResponse;
	private List<StResponse> stResponses = new ArrayList<>();

	public StObserver(
		Callback userCallback,
		String requestType,
		boolean multipleCallsMerged
	) {
		this.userCallback = userCallback;
		this.requestType = requestType;
		this.multipleCallsMerged = multipleCallsMerged;
	}

	@Override
	public void onCompleted() {
		Object result;

		switch(requestType) {
			case PLACES_API_CALL:
				List<Place> places = new ArrayList<>();
				for(StResponse stResponse : stResponses) {
					places.addAll(stResponse.getData().getPlaces());
				}
				result = places;
				break;

			case DETAIL_API_CALL:
				result = stResponse.getData().getDetail();
				break;
			case MEDIA_API_CALL:
				result = stResponse.getData().getMedia();
				break;
			default:
				result = null;
				break;
		}

		userCallback.onSuccess(result);
	}

	@Override
	public void onError(Throwable e) {
		userCallback.onFailure(e);
	}

	@Override
	public void onNext(Result<StResponse> stResponseResult) {
		if(isError(stResponseResult)){
			Log.e(TAG, "Error: " + stResponseResult.response().errorBody().toString());
		} else {
			if(multipleCallsMerged){
				stResponses.add(stResponseResult.response().body());
			} else {
				stResponse = stResponseResult.response().body();
			}
		}
	}

	private boolean isError(Result<StResponse> stResponseResult) {
		if(stResponseResult.error() != null){
			return true;
		} else {
			if(stResponseResult.response().errorBody() != null){
				return true;
			} else {
				return stResponseResult.response().body() == null;
			}
		}
	}
}
