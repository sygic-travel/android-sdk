package com.sygic.travel.sdk.model.api;

import java.util.List;

/**
 * <p>Base of an API response. It's extended by specific respond classes containing the specific data.
 * It's abstract, because of the abstract {@link #getData()} method.</p>
 */
public abstract class StResponse {
	public static final int STATUS_OK = 200;

	private int statusCode;
	private Error error;

	public int getStatusCode() {
		return statusCode;
	}

	public Error getError() {
		return error;
	}

	/**
	 * This method ought to be implemented in every class extending {@code StResponse}.
	 * @return Response data object, which type depends on a class, which extends {@code StResponse}.
	 */
	public abstract Object getData();

	/**
	 * Contains an error details - in case that an error has occurred.
	 */
	public class Error {
		private String id;
		private List<String> args;

		public String getId() {
			return id;
		}

		public List<String> getArgs() {
			return args;
		}
	}
}
