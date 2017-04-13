package com.sygic.travel.sdk.geo.spread;

import android.graphics.Point;

import com.sygic.travel.sdk.model.place.Place;

/**
 * Created by michal.murin on 13.4.2017.
 */

public class SpreadedPlace {
	private Place place;
	private Point canvasCoors;
	private SpreadSizeConfig sizeConfig;

	public SpreadedPlace(Place place, Point canvasCoors, SpreadSizeConfig sizeConfig) {
		this.place = place;
		this.canvasCoors = canvasCoors;
		this.sizeConfig = sizeConfig;
	}

	public Place getPlace() {
		return place;
	}

	public void setPlace(Place place) {
		this.place = place;
	}

	public Point getCanvasCoors() {
		return canvasCoors;
	}

	public void setCanvasCoors(Point canvasCoors) {
		this.canvasCoors = canvasCoors;
	}

	public SpreadSizeConfig getSizeConfig() {
		return sizeConfig;
	}

	public void setSizeConfig(SpreadSizeConfig sizeConfig) {
		this.sizeConfig = sizeConfig;
	}
}
