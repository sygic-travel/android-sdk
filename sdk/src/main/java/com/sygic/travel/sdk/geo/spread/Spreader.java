package com.sygic.travel.sdk.geo.spread;

import android.graphics.Point;

import com.sygic.travel.sdk.model.geo.BoundingBox;
import com.sygic.travel.sdk.model.geo.Location;
import com.sygic.travel.sdk.model.place.Place;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by michal.murin on 13.4.2017.
 */

public class Spreader {
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

			Point canvasCoors = locationToCanvasCoors(place.getLocation(), bounds, canvasSize);
			if(
				canvasCoors.x < 0 ||
				canvasCoors.y < 0 ||
				canvasCoors.x > canvasSize.width ||
				canvasCoors.y > canvasSize.height
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
				if(!intersects(sizeConfig, canvasCoors, visiblePlaces)) {
					visiblePlaces.add(0, new SpreadedPlace(place, canvasCoors, sizeConfig));
				}
			}
		}

		return new SpreadResult(visiblePlaces, hiddenPlaces);
	}

	private boolean intersects(
		SpreadSizeConfig sizeConfig,
		Point canvasCoors,
		List<SpreadedPlace> spreadedPlaces
	) {
		boolean intersects;
		int r2;
		int r = sizeConfig.getRadius() + sizeConfig.getMargin();

		for(SpreadedPlace spreadedPlace : spreadedPlaces) {
			int dX = canvasCoors.x - spreadedPlace.getCanvasCoors().x;
			int dY = canvasCoors.y - spreadedPlace.getCanvasCoors().y;

			r2 = spreadedPlace.getSizeConfig().getRadius() + spreadedPlace.getSizeConfig().getMargin();
			intersects = (Math.pow(dX, 2) +	Math.pow(dY, 2)) <=	Math.pow(r + r2, 2);
			if(intersects) {
				return true;
			}
		}
		return false;
	}

	private Point locationToCanvasCoors(
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
