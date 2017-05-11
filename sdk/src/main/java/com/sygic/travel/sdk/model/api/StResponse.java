package com.sygic.travel.sdk.model.api;

import java.util.List;

/**
 * <p>Base of an API response. It's extended by specific respond classes containing the specific data.
 * It's abstract, because of the abstract {@link #getData()} method.</p>
 */
public abstract class StResponse {
	private String status;
	private int statusCode;
	private String statusMessage;
	private Error error;

	public String getStatus() {
		return status;
	}

	public int getStatusCode() {
		return statusCode;
	}

	public String getStatusMessage() {
		return statusMessage;
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
