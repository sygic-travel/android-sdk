package com.sygic.travel.sdk.model.api;

import com.google.gson.annotations.SerializedName;
import com.sygic.travel.sdk.model.place.Detail;

import java.util.List;

/**
 * <p>Response that contains list of detailed places data. Suitable for showing details in a pager or
 * synchronization.</p>
 */
public class PlacesDetailedResponse extends StResponse {
	private Data data;

	public Object getData() {
		return data.getPlaces();
	}

	/**
	 * Contains multiple detailed places' data from an API response.
	 */
	private class Data {
		static final String PLACES = "places";

		@SerializedName(PLACES)
		private List<Detail> places;

		public List<Detail> getPlaces() {
			return places;
		}
	}
}
