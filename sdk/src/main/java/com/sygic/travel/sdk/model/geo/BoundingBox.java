package com.sygic.travel.sdk.model.geo;

import com.google.gson.annotations.SerializedName;

/**
 * Created by michal.murin on 16.2.2017.
 */

/*
class BoundingBox {
    north: float
    east: float
    south: float
    west: float
}
*/

public class BoundingBox {
	public static final String NORTH = "north";
	public static final String EAST = "east";
	public static final String SOUTH = "south";
	public static final String WEST = "west";

	@SerializedName(NORTH)
	private float north;

	@SerializedName(EAST)
	private float east;

	@SerializedName(SOUTH)
	private float south;

	@SerializedName(WEST)
	private float west;

	public BoundingBox() {
	}

	public float getNorth() {
		return north;
	}

	public void setNorth(float north) {
		this.north = north;
	}

	public float getEast() {
		return east;
	}

	public void setEast(float east) {
		this.east = east;
	}

	public float getSouth() {
		return south;
	}

	public void setSouth(float south) {
		this.south = south;
	}

	public float getWest() {
		return west;
	}

	public void setWest(float west) {
		this.west = west;
	}
}
