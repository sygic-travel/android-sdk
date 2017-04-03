package com.sygic.travel.sdkdemo.utils.spread;

import android.graphics.Rect;

/**
 * Created by michal.murin on 3.4.2017.
 */

public class Intersection {
	private DimensConfig dimensConfig;

	public Intersection(DimensConfig dimensConfig) {
		this.dimensConfig = dimensConfig;
	}

	public Rect getMaxRect(PlaceMeta placeMeta){
		int size = getMargin(placeMeta.getSize());
		int coordinates[] = placeMeta.getCoordinates();
		int x = coordinates[0] - size;
		int y = coordinates[1] - size;
		return new Rect(x, y, x + (size << 1), y + (size << 1));
	}

	public boolean intersects(PlaceMeta placeMeta1, PlaceMeta placeMeta2){
		if(placeMeta1.getSize().equals(PlacesSpreader.INVISIBLE)){
			return false;
		}
		int center1[] = placeMeta1.getCoordinates();
		int center2[] = placeMeta2.getCoordinates();

		int size1 = getPlaceSize(placeMeta1.getSize());
		int size2 = getPlaceSize(placeMeta2.getSize());

		int box = getMargin(placeMeta1.getSize());

		int r1 = (size1 >> 1) + (box << 1);
		int r2 = size2 >> 1;

		return (Math.pow(center1[0] - center2[0], 2) + Math.pow(center1[1] - center2[1], 2)) <= Math.pow(r1 + r2, 2);
	}

	private int getMargin(String placeSize){
		switch(placeSize) {
			case PlacesSpreader.SMALL:
				return dimensConfig.getSmallMargin();
			case PlacesSpreader.MEDIUM:
				return dimensConfig.getMediumMargin();
			case PlacesSpreader.BIG:
				return dimensConfig.getBigMargin();
			case PlacesSpreader.POPULAR:
				return dimensConfig.getPopularMargin();
			default:
				return 0;
		}
	}

	private int getPlaceSize(String placeSize){
		switch(placeSize) {
			case PlacesSpreader.SMALL:
				return dimensConfig.getSmallSize();
			case PlacesSpreader.MEDIUM:
				return dimensConfig.getMediumSize();
			case PlacesSpreader.BIG:
				return dimensConfig.getBigSize();
			case PlacesSpreader.POPULAR:
				return dimensConfig.getPopularSize();
			default:
				return 0;
		}
	}
}
