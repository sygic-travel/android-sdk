package com.sygic.travel.sdk.model.geo;

import com.google.gson.annotations.SerializedName;

/**
 * Created by michal.murin on 16.2.2017.
 */

/*
class Location {
    lat: float
    lng: float
}
*/

public class Location {
	public static final String LAT = "lat";
	public static final String LNG = "lng";

	@SerializedName(LAT)
	private float lat;

	@SerializedName(LNG)
	private float lng;

	public Location() {
	}

	public float getLat() {
		return lat;
	}

	public void setLat(float lat) {
		this.lat = lat;
	}

	public float getLng() {
		return lng;
	}

	public void setLng(float lng) {
		this.lng = lng;
	}
}
