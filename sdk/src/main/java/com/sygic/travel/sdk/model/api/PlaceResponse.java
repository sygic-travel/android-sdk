package com.sygic.travel.sdk.model.api;

import com.google.gson.annotations.SerializedName;
import com.sygic.travel.sdk.model.place.Detail;

/**
 * <p>Response that contains one detailed place data. Suitable for showing a place detail.</p>
 */
public class PlaceResponse extends StResponse {
	private Data data;

	public Data getData() {
		return data;
	}

	/**
	 * Contains one place's detailed data from an API response.
	 */
	public class Data {
		static final String PLACE = "place";

		@SerializedName(PLACE)
		private Detail detail;

		public Detail getDetail() {
			return detail;
		}
	}
}
