package com.sygic.travel.sdkdemo.utils.spread;

import android.content.res.Resources;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.Log;

import com.sygic.travel.sdk.model.geo.BoundingBox;
import com.sygic.travel.sdk.model.geo.Location;
import com.sygic.travel.sdk.model.place.Place;
import com.sygic.travel.sdkdemo.utils.Utils;

import static com.sygic.travel.sdkdemo.map.MapsActivity.ZOOM_FOR_DETAIL;

/**
 * Created by michal.murin on 3.4.2017.
 */

public class PlacesSpreader {
	private static final String TAG = PlacesSpreader.class.getSimpleName();

	public static final String POPULAR = "popular";
	public static final String SMALL = "small";
	public static final String MEDIUM = "medium";
	public static final String BIG = "big";
	public static final String INVISIBLE = "invisible";

	private String markerSizesTypes[] = new String[]{POPULAR, BIG, MEDIUM, SMALL};
	private final Resources resources;
	private Intersection intersection;

	private QuadTree quadTree;
	private int mapWidth;
	private int mapHeight;
	private BoundingBox boundingBox;
	private int zoom;

	public PlacesSpreader(
		Resources resources,
		int mapWidth,
		int mapHeight,
		DimensConfig dimensConfig
	){
		this.resources = resources;
		this.mapWidth = mapWidth;
		this.mapHeight = mapHeight;

		intersection = new Intersection(dimensConfig);
		quadTree = new QuadTree(intersection, new Rect(0, 0, mapWidth, mapHeight));
	}

	public void setNewMapPosition(
		BoundingBox boundingBox,
		int zoom
	){
		this.boundingBox = boundingBox;
		this.zoom = zoom;
		quadTree.reset();
	}

	public int getPlaceSizeAndInsert(Place place){
		Location location = place.getLocation();

		int suitableSizesCount =
			zoom >= ZOOM_FOR_DETAIL ? markerSizesTypes.length - 1 : markerSizesTypes.length;
		Point screenPosition = latLngToPixel(location.getLat(), location.getLng(), boundingBox);

		return insert(screenPosition, getPlaceTier(place), suitableSizesCount);
	}

	private int insert(Point screenPosition, int tier, int suitableSizesCount){
		String sizeType = zoom >= ZOOM_FOR_DETAIL ? MEDIUM : SMALL;
		int limitSizeIndex = getLimitSizeIndex(tier);
		for(int i = limitSizeIndex; i < suitableSizesCount; i++) {
			PlaceMeta placeMeta = new PlaceMeta(screenPosition.x, screenPosition.y, markerSizesTypes[i]);
			int result = quadTree.insert(placeMeta);
			if(result == QuadTree.INSERTED) {
				sizeType = markerSizesTypes[i];
				break;
			} else if(result == QuadTree.FAIL_OUT_OF_BOUNDS) {
				sizeType = INVISIBLE;
				break;
			}
		}
		Log.d(TAG, sizeType);
		return Utils.getMarkerSize(resources, sizeType);
	}

	private int getPlaceTier(Place place) {
		float rating = place.getRating();
		if(rating >= 8.5f) {
			return 1;
		} else if(rating >= 7f) {
			return 2;
		} else if(rating >= 4.5f) {
			return 3;
		} else {
			return 4;
		}
	}

	private int getLimitSizeIndex(int tier){
		switch(tier){
			case 1:
				return 0;
			case 2:
				return 1;
			case 3:
				return 2;
			case 4:
				return zoom <= 14 ? 3 : 2;
		}
		return 3;
	}

	/**
	 * If using Google Maps, you can use GoogleMap.getProjection().toScreenLocation(LatLng)
	 *
	 * @param lat Latitude of the posiotion to convert
	 * @param lng Longitude of the position to convert
	 * @param boundingBox map's current bounding box
	 * @return android.graphics.Point device's screen coordinates
	 *
	 */
	private Point latLngToPixel(double lat, double lng, BoundingBox boundingBox){
		double south, west, north, east;
		south = boundingBox.getSouth();
		west = boundingBox.getWest();
		north = boundingBox.getNorth();
		east = boundingBox.getEast();

		double latDiff = north - lat;
		double lngDiff = lng - west;

		double latRatio = mapHeight / Math.abs(south - north);
		double lngRatio = mapWidth / Math.abs(west - east);

		if (west > east){ //date border
			lngRatio = mapWidth / Math.abs(180 - west + 180 + east);
			if(lng < 0 && lng < east){
				lngDiff = 180 - west + 180 + lng;
			}
			if(lng > 0 && lng < west){
				lngDiff = 180 - west + 180 + lng;
			}
		}

		return new Point(
			(int) (lngDiff * lngRatio),
			(int) (latDiff * latRatio)
		);
	}
}
