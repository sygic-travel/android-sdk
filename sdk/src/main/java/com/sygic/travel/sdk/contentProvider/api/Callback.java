package com.sygic.travel.sdk.contentProvider.api;

/**
 * <p>Callback class used for API requests. Contains {@link Callback#onSuccess(Object)} method,
 * {@link Callback#onFailure(Throwable)} method respectively, which ought to be implemented for
 * each use case.</p>
 * @param <T> Generic data type of result.
 */
public abstract class Callback<T> {
	public abstract void onSuccess(T data);
	public abstract void onFailure(Throwable t);
}
