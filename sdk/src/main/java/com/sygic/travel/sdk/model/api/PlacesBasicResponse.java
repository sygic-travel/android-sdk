package com.sygic.travel.sdk.model.api;

import com.google.gson.annotations.SerializedName;
import com.sygic.travel.sdk.model.place.Place;

import java.util.List;

/**
 * <p>Response that contains list of basic places data (without detailed data). Suitable for showing
 * places in a list or spread on a map.</p>
 */
public class PlacesBasicResponse extends StResponse {
	private Data data;

	public Data getData() {
		return data;
	}

	/**
	 * Contains multiple places' basic data from an API response.
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
