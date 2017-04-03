package com.sygic.travel.sdkdemo.utils.spread;

/**
 * Created by michal.murin on 3.4.2017.
 */

public class PlaceMeta {
	private int x,y;
	private String size;

	public PlaceMeta(int x, int y, String size) {
		this.x = x;
		this.y = y;
		this.size = size;
	}

	public String getSize() {
		return size;
	}

	public int[] getCoordinates(){
		return new int[]{x, y};
	}
}
