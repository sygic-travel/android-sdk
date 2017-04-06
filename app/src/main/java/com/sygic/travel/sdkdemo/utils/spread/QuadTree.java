package com.sygic.travel.sdkdemo.utils.spread;

import android.graphics.Rect;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by michal.murin on 3.4.2017.
 */

public class QuadTree {
	public static final int FAIL_OUT_OF_BOUNDS = -2;
	public static final int FAIL_INTERSECTS = -1;
	public static final int INSERTED = 1;

	private Intersection intersection;
	private Rect mapRect;
	private List<PlaceMeta> insertedPlaceMetas;

	public QuadTree(Intersection intersection, Rect mapRect) {
		this.intersection = intersection;
		this.mapRect = mapRect;
		reset();
	}

	public void reset(){
		insertedPlaceMetas = Collections.synchronizedList(new ArrayList<PlaceMeta>());
	}

	public boolean intersectsWithInsertedPlaces(PlaceMeta placeMeta){
		for (PlaceMeta insertedPlaceMeta : insertedPlaceMetas){
			if(intersection.intersects(insertedPlaceMeta, placeMeta)){
				return true;
			}
		}
		return false;
	}

	public int insert(PlaceMeta placeMeta){
		Rect rect = intersection.getMaxRect(placeMeta);
		if(!mapRect.contains(rect) && !Rect.intersects(mapRect, rect)){
			return FAIL_OUT_OF_BOUNDS;
		}

		if(intersectsWithInsertedPlaces(placeMeta)){
			return FAIL_INTERSECTS;
		}

		insertedPlaceMetas.add(placeMeta);

		return INSERTED;
	}
}
