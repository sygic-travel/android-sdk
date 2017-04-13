package com.sygic.travel.sdk.geo;

import android.graphics.Point;

import com.sygic.travel.sdk.geo.spread.CanvasSize;
import com.sygic.travel.sdk.model.geo.BoundingBox;
import com.sygic.travel.sdk.model.geo.Location;

/**
 * Created by michal.murin on 13.4.2017.
 */

public class GeoUtils {
	public static Point locationToCanvasCoors(
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
		double lngRatio = canvasSize.width / Math.abs(west - east);

		if (west > east){ //date border
			lngRatio = canvasSize.width / Math.abs(180 - west + 180 + east);
			if(location.getLng() < 0 && location.getLng() < east){
				lngDiff = 180 - west + 180 + location.getLng();
			}
			if(location.getLng() > 0 && location.getLng() < west){
				lngDiff = 180 - west + 180 + location.getLng();
			}
		}

		return new Point(
			(int) (lngDiff * lngRatio),
			(int) (latDiff * latRatio)
		);
	}
}
