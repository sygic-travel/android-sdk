package com.sygic.travel.sdk.model.api;

import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.sygic.travel.sdk.Parser;
import com.sygic.travel.sdk.model.place.Place;

import java.util.List;

/**
 * <p>Response that contains a list of basic places data (without detailed data). Suitable for showing
 * places in a list or spread on a map.</p>
 */
public class PlacesResponse extends StResponse {
	private static final String PLACES = "places";
	private List<Place> places;

	private JsonObject data;

	public Object getData() {
		if(places == null && data != null) {
			places = Parser.parseJson(
				data.get(PLACES).toString(),
				new TypeToken<List<Place>>(){}.getType()
			);
		}
		return places;
	}
}
