package com.sygic.travel.sdk.geo.spread;

import android.graphics.Point;

import com.sygic.travel.sdk.model.place.Place;

/**
 * <p>Wrapper of {@link Place}, which has been already processed by the spread algorithm.</p>
 */
public class SpreadedPlace {
	private Place place;
	private Point canvasCoords;
	private SpreadSizeConfig sizeConfig;

	/**
	 * <p>Wrapper of {@link Place}, which has been already processed by the spread algorithm.</p>
	 * @param place A processed place.
	 * @param canvasCoords Place's {@code x, y} coordinates on map's canvas on display.
	 * @param sizeConfig Place's {@link SpreadSizeConfig}.
	 */
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
