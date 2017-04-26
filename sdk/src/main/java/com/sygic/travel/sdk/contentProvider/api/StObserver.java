package com.sygic.travel.sdk.contentProvider.api;

import android.util.Log;

import com.sygic.travel.sdk.StSDK;
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
 * <p>Observer which subscribes to receive a response from API.</p>
 */
public class StObserver implements Observer<Result<StResponse>> {
	private static final String TAG = StObserver.class.getSimpleName();

	private String requestType;
	private Callback userCallback;
	private boolean multipleCallsMerged;

	private StResponse stResponse;
	private List<StResponse> stResponses = new ArrayList<>();

	/**
	 * @param userCallback Callback, which methods are called, when the response is processed.
	 * @param requestType Type of API request.
	 * @param multipleCallsMerged Flag indicating whether the Observer hes been subscribed to more
	 *                            than 1 request.
	 */
	public StObserver(
		Callback userCallback,
		String requestType,
		boolean multipleCallsMerged
	) {
		this.userCallback = userCallback;
		this.requestType = requestType;
		this.multipleCallsMerged = multipleCallsMerged;
	}

	/**
	 * <p>All API requests have been finished.</p>
	 */
	@Override
	public void onCompleted() {
		Object result;

		if(stResponse == null || stResponse.getData() == null){
			onError(new Exception());
		}

		switch(requestType) {
			case PLACES_API_CALL:
				result = stResponse.getData().getPlaces();
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

	/**
	 * <p>A critical error occured.</p>
	 */
	@Override
	public void onError(Throwable e) {
		userCallback.onFailure(e);
	}

	/**
	 * <p>A single API request has been finished.</p>
	 */
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

	/**
	 * <p>Determines if an error occured while requesting API.</p>
	 * @param stResponseResult Result from an API request.
	 * @return {@code true} if an error occured, {@code false} otherwise.
	 */
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
