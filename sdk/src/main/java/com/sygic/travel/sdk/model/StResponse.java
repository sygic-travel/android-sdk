package com.sygic.travel.sdk.model;

import com.sygic.travel.sdk.model.place.Place;

import java.util.List;

public class StResponse {
	public String status;
	public int statusCode;
	public String statusMessage;
	public Data data;

	public String getStatus() {
		return status;
	}

	public int getStatusCode() {
		return statusCode;
	}

	public String getStatusMessage() {
		return statusMessage;
	}

	public Data getData() {
		return data;
	}

	/**
	 * Only one of attributes should have assigned value
	 */
	public class Data {
		private List<Place> places;
		private Place place;

		public List<Place> getPlaces() {
			return places;
		}

		public Place getPlace() {
			return place;
		}
	}
}
