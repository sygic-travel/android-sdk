package com.sygic.travel.sdk.model.api;

import java.util.List;

/**
 * <p>Base of an API response. It's extended by specific respond classes containing the specific data.</p>
 */
public class StResponse {
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
