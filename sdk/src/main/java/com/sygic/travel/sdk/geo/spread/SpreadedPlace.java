package com.sygic.travel.sdk.geo.spread;

import android.graphics.Point;

import com.sygic.travel.sdk.model.place.Place;

/**
 * Created by michal.murin on 13.4.2017.
 */

public class SpreadedPlace {
	private Place place;
	private Point canvasCoords;
	private SpreadSizeConfig sizeConfig;

	public SpreadedPlace(Place place, Point canvasCoords, SpreadSizeConfig sizeConfig) {
		this.place = place;
		this.canvasCoords = canvasCoords;
		this.sizeConfig = sizeConfig;
	}

	public Place getPlace() {
		return place;
	}

	public void setPlace(Place place) {
		this.place = place;
	}

	public Point getCanvasCoords() {
		return canvasCoords;
	}

	public void setCanvasCoords(Point canvasCoords) {
		this.canvasCoords = canvasCoords;
	}

	public SpreadSizeConfig getSizeConfig() {
		return sizeConfig;
	}

	public void setSizeConfig(SpreadSizeConfig sizeConfig) {
		this.sizeConfig = sizeConfig;
	}
}
