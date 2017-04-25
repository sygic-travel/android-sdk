package com.sygic.travel.sdk.geo.spread;

import android.graphics.Point;

import com.sygic.travel.sdk.model.geo.BoundingBox;
import com.sygic.travel.sdk.model.geo.Location;
import com.sygic.travel.sdk.model.place.Place;

import java.util.LinkedList;
import java.util.List;

public class Spreader {

	/**
	 * Generates a list of spreaded places.
	 * @param places Places to spread.
	 * @param sizeConfigs Size configurations available for spreading.
	 * @param bounds Map's bounds, which the places are supposed to be spreaded within.
	 * @param canvasSize Map's canvas size on display.
	 * @return Spreaded places as {@link SpreadResult}.
	 */
	public SpreadResult spread(
		List<Place> places,
		List<SpreadSizeConfig> sizeConfigs,
		BoundingBox bounds,
		CanvasSize canvasSize
	){
		LinkedList<SpreadedPlace> visiblePlaces = new LinkedList<>();
		LinkedList<Place> hiddenPlaces = new LinkedList<>();

		for(Place place : places) {
			if(!place.hasLocation()){
				hiddenPlaces.add(0, place);
				continue;
			}

			Point canvasCoords = locationToCanvasCoords(place.getLocation(), bounds, canvasSize);
			if(
				canvasCoords.x < 0 ||
				canvasCoords.y < 0 ||
				canvasCoords.x > canvasSize.width ||
				canvasCoords.y > canvasSize.height
			) {
				hiddenPlaces.add(0, place);
				continue;
			}

			for(SpreadSizeConfig sizeConfig : sizeConfigs) {
				if(sizeConfig.isPhotoRequired() && !place.hasThumbnailUrl()) {
					continue;
				}
				if(sizeConfig.getMinimalRating() > 0f &&
					place.getRating() >= sizeConfig.getMinimalRating()
				) {
					continue;
				}
				if(!intersects(sizeConfig, canvasCoords, visiblePlaces)) {
					visiblePlaces.add(0, new SpreadedPlace(place, canvasCoords, sizeConfig));
				}
			}
		}

		return new SpreadResult(visiblePlaces, hiddenPlaces);
	}

	/**
	 * Determines a given size configuration intersects with any of already spreaded places.
	 * @param sizeConfig Size configuration of a place.
	 * @param canvasCoords Place's coordinates on canvas.
	 * @param spreadedPlaces Already spreaded places.
	 * @return
	 * <b>True</b> if place with given size configuration would intersect with any of already spreaded places.<br/>
	 * <b>False</b> otherwise - place can be displayed on a map with given {@param sizeConfig}
	 */
	private boolean intersects(
		SpreadSizeConfig sizeConfig,
		Point canvasCoords,
		List<SpreadedPlace> spreadedPlaces
	) {
		boolean intersects;
		int r2;
		int r = sizeConfig.getRadius() + sizeConfig.getMargin();

		for(SpreadedPlace spreadedPlace : spreadedPlaces) {
			int dX = canvasCoords.x - spreadedPlace.getCanvasCoords().x;
			int dY = canvasCoords.y - spreadedPlace.getCanvasCoords().y;

			r2 = spreadedPlace.getSizeConfig().getRadius() + spreadedPlace.getSizeConfig().getMargin();
			intersects = (Math.pow(dX, 2) +	Math.pow(dY, 2)) <=	Math.pow(r + r2, 2);
			if(intersects) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Converts given location within given bounds to {@code x, y} coordinates within given canvas.
	 * @param location Location which is supposed be converted to {@code x, y} coordinates.
	 * @param boundingBox Bounds the location lies within.
	 * @param canvasSize Size of a canvas the coordinates are supposed to lie within.
	 * @return
	 */
	private Point locationToCanvasCoords(
		Location location,
		BoundingBox boundingBox,
		CanvasSize canvasSize
	){
		double south, west, north, east;
		south = boundingBox.getSouth();
		west = boundingBox.getWest();
		north = boundingBox.getNorth();
		east = boundingBox.getEast();

		double latDiff = north - location.getLat();
		double lngDiff = location.getLng() - west;

		double latRatio = canvasSize.height / Math.abs(south - north);
		double lngRatio;

		if (west > east){ //date border
			lngRatio = canvasSize.width / Math.abs(180 - west + 180 + east);
			if(location.getLng() < 0 && location.getLng() < east){
				lngDiff = 180 - west + 180 + location.getLng();
			}
			if(location.getLng() > 0 && location.getLng() < west){
				lngDiff = 180 - west + 180 + location.getLng();
			}
		} else {
			lngRatio = canvasSize.width / Math.abs(west - east);
		}

		return new Point(
			(int) (lngDiff * lngRatio),
			(int) (latDiff * latRatio)
		);
	}
}
