package com.sygic.travel.sdk.model.api;

import com.google.gson.annotations.SerializedName;
import com.sygic.travel.sdk.model.place.Place;

import java.util.List;

/**
 * <p>Response that contains list of detailed places data. Suitable for showing details in a pager or
 * synchronization.</p>
 */
public class PlacesResponse extends StResponse {
	private Data data;

	public Data getData() {
		return data;
	}

	/**
	 * Contains multiple detailed places' data from an API response.
	 */
	public class Data {
		static final String PLACES = "places";

		@SerializedName(PLACES)
		private List<Place> places;

		public List<Place> getPlaces() {
			return places;
		}
	}
}
