package com.sygic.travel.sdk.contentProvider.api;

import com.sygic.travel.sdk.model.api.StResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.adapter.rxjava.Result;
import rx.Observer;

/**
 * <p>Observer which subscribes to receive a response from API.</p>
 * @param <RT> Response type - must be one of the response classes extending {@link StResponse}.
 */
public class StObserver<RT extends StResponse> implements Observer<Result<RT>> {
	private static final String TAG = StObserver.class.getSimpleName();
	private static final String STATUS_ERROR = "error";

	private Callback userCallback;
	private boolean multipleCallsMerged;

	private RT stResponse;
	private List<RT> stResponses = new ArrayList<>();

	/**
	 * @param userCallback Callback, which methods are called, when the response is processed.
	 * @param multipleCallsMerged Flag indicating whether the Observer hes been subscribed to more
	 *                            than 1 request.
	 */
	public StObserver(
		Callback userCallback,
		boolean multipleCallsMerged
	) {
		this.userCallback = userCallback;
		this.multipleCallsMerged = multipleCallsMerged;
	}

	/**
	 * <p>All API requests have been finished - not necesserily successfully. However an error could
	 * have occurred, so the response must be checked.</p>
	 */
	@Override
	public void onCompleted() {
		if(stResponse == null || (stResponse.getStatus() != null && stResponse.getStatus().equals(STATUS_ERROR))){
			return;
		}

		userCallback.onSuccess(stResponse.getData());
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
	public void onNext(Result<RT> stResponseResult) {
		if(multipleCallsMerged){
			if(!isError(stResponseResult)) {
				stResponses.add(stResponseResult.response().body());
			}
		} else {
			if(isError(stResponseResult)){
				userCallback.onFailure(new Exception(getErrorMessage(stResponseResult)));
			} else {
				stResponse = stResponseResult.response().body();
			}
		}
	}

	/**
	 * <p>This method is called only if an error has occured. An API result is passed as an argument
	 * and an error message is created.</p>
	 * @param stResponseResult Result from of an API request.
	 * @return An error message.
	 */
	private String getErrorMessage(Result<RT> stResponseResult) {
		StringBuilder error = new StringBuilder("Error: ");
		if(stResponseResult.response() != null && stResponseResult.response().body() != null){
			error.append(stResponseResult.response().body().getStatusCode());
			error.append(": ");
			error.append(stResponseResult.response().body().getError().getId());
		} else if(stResponseResult.response().errorBody() != null) {
			try {
				error.append(stResponseResult.response().errorBody().string());
			} catch(IOException e) {
				error.append(stResponseResult.response().errorBody().toString());
			}
		} else if(stResponseResult.isError()) {
			error.append(stResponseResult.error().getMessage());
		}
		return error.toString();
	}

	/**
	 * <p>Determines if an error occured while requesting API.</p>
	 * @param stResponseResult Result from of an API request.
	 * @return {@code true} if an error occured, {@code false} otherwise.
	 */
	private boolean isError(Result<RT> stResponseResult) {
		if(stResponseResult.isError()){
			return true;
		} else {
			if(stResponseResult.response().errorBody() != null){
				return true;
			} else if(stResponseResult.response().body() != null){
				// success responses don't have status
				final String status = stResponseResult.response().body().getStatus();
				return (status != null && status.equals(STATUS_ERROR));
			} else {
				return true;
			}
		}
	}
}
