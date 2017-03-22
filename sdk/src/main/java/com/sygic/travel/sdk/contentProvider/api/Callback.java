package com.sygic.travel.sdk.contentProvider.api;

/**
 * Clients should use this class
 * @param <T>
 */
public abstract class Callback<T> {
	public abstract void onSuccess(T data);
	public abstract void onFailure(Throwable t);
}
