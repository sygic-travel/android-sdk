package com.sygic.travel.sdkdemo.utils.spread;

import android.content.res.Resources;
import android.graphics.Rect;

import com.sygic.travel.sdk.model.geo.BoundingBox;
import com.sygic.travel.sdk.model.geo.Location;
import com.sygic.travel.sdk.model.place.Place;
import com.sygic.travel.sdkdemo.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import static com.sygic.travel.sdkdemo.map.MapsActivity.ZOOM_FOR_DETAIL;

/**
 * Created by michal.murin on 3.4.2017.
 */

public class PlacesSpreader {
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
	private List<String> processedGuids;


	public PlacesSpreader(Resources resources, DimensConfig dimensConfig){
		this.resources = resources;
		intersection = new Intersection(dimensConfig);
	}

	public void prepareForSpreadingNextCollection(BoundingBox boundingBox, int zoom, int mapWidth, int mapHeight){
		this.boundingBox = boundingBox;
		this.zoom = zoom;
		this.mapWidth = mapWidth;
		this.mapHeight = mapHeight;

		Rect mapRect = new Rect(0, 0, mapWidth, mapHeight);
		quadTree = new QuadTree(intersection, mapRect);
		processedGuids = new ArrayList<>();
	}

	public int getPlaceSizeAndInsert(Place place){
		Location location = place.getLocation();
		if(processedGuids.contains(place.getGuid()) || location == null){
			return Utils.getMarkerSize(resources, INVISIBLE);
		}
		int suitableSizesCount =
			zoom >= ZOOM_FOR_DETAIL ? markerSizesTypes.length - 1 : markerSizesTypes.length;
		int screenPosition[] = latLngToPixel(location.getLat(), location.getLng(), boundingBox);

		processedGuids.add(place.getGuid());

		return insert(screenPosition, getPlaceTier(place), suitableSizesCount);
	}

	private int insert(int [] screenPosition, int tier, int suitableSizesCount){
		String sizeType = zoom >= ZOOM_FOR_DETAIL ? MEDIUM : INVISIBLE;
		int limitSizeIndex = getLimitSizeIndex(tier);
		for(int i = limitSizeIndex; i < suitableSizesCount; i++) {
			PlaceMeta placeMeta = new PlaceMeta(screenPosition[0], screenPosition[1], markerSizesTypes[i]);
			int result = quadTree.insert(placeMeta);
			if(result == QuadTree.INSERTED) {
				sizeType = markerSizesTypes[i];
				break;
			} else if(result == QuadTree.FAIL_OUT_OF_BOUNDS) {
				sizeType = INVISIBLE;
				break;
			}
		}
		return Utils.getMarkerSize(resources, sizeType);
	}

	private int getPlaceTier(Place place) {
		float rating = place.getRating();
		if(rating >= 7.5f) {
			return 1;
		} else if(rating >= 5f) {
			return 2;
		} else if(rating >= 2.5f) {
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

	private int[] latLngToPixel(double lat, double lng, BoundingBox boundingBox){
		double south, west, north, east;
		south = boundingBox.getSouth();
		west = boundingBox.getWest();
		north = boundingBox.getNorth();
		east = boundingBox.getEast();

		double latDiff = north - lat;
		double lngDiff = lng - west;

		double latRatio = mapHeight / Math.abs(south - north);
		double lngRatio = mapWidth / Math.abs(west - east);

		if (west > east){ //date date border is crossing
			lngRatio = mapWidth / Math.abs(180 - west + 180 + east);
			if(lng < 0 && lng < east){
				lngDiff = 180 - west + 180 + lng;
			}
			if(lng > 0 && lng < west){
				lngDiff = 180 - west + 180 + lng;
			}
		}

		int x = (int) (lngDiff * lngRatio);
		int y = (int) (latDiff * latRatio);
		return new int[]{x, y};
	}
}
