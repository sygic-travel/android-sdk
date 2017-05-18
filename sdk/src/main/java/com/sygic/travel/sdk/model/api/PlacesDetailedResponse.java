package com.sygic.travel.sdk.model.api;

import com.google.gson.JsonObject;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;
import com.sygic.travel.sdk.Parser;
import com.sygic.travel.sdk.model.place.Detail;
import com.sygic.travel.sdk.model.place.Place;

import java.lang.reflect.Type;
import java.util.List;

/**
 * <p>Response containing a list of detailed places data. Suitable for showing details in a pager or
 * synchronization.</p>
 */
public class PlacesDetailedResponse extends StResponse {
	private static final String PLACE = "place";
	private List<Place> places;

	private JsonObject data;

	public Object getData() {
		if(places == null && data != null) {
			final String dataJson = data.get(PLACE).toString();
			final Type placesTypeToken = new TypeToken<List<Place>>(){}.getType();
			final Type detailsTypeToken = new TypeToken<List<Detail>>(){}.getType();

			List<Detail> details = Parser.parseJson(dataJson, detailsTypeToken);
			places = Parser.parseJson(dataJson, placesTypeToken);

			int placesSize = places.size();
			for(int i = 0; i < placesSize; i++) {
				places.get(i).setDetail(details.get(i));
			}
		}
		return places;
	}
}
