package com.sygic.travel.sdk.model.api;

import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.sygic.travel.sdk.Parser;
import com.sygic.travel.sdk.model.place.Detail;
import com.sygic.travel.sdk.model.place.Place;

import java.lang.reflect.Type;

/**
 * <p>Response containing one detailed place data. Suitable for showing a place detail.</p>
 */
public class PlaceDetailedResponse extends StResponse {
	private static final String PLACE = "place";
	private Place place;

	private JsonObject data;

	public Object getData() {
		if(place == null && data != null) {
			final String dataJson = data.get(PLACE).toString();
			final Type placeTypeToken = new TypeToken<Place>(){}.getType();
			final Type detailTypeToken = new TypeToken<Detail>(){}.getType();

			Detail detail = Parser.parseJson(dataJson, detailTypeToken);
			place = Parser.parseJson(dataJson, placeTypeToken);
			place.setDetail(detail);
		}
		return place;
	}
}
