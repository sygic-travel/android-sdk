package com.sygic.travel.sdk.geo.quadkey;

import com.sygic.travel.sdk.model.geo.BoundingBox;
import com.sygic.travel.sdk.model.geo.Location;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by michal.murin on 6.4.2017.
 */

public class QuadkeysGenerator {
	public static List<String> generateQuadkeys(BoundingBox boundingBox, int zoom){
		List<String> quadkeys = new ArrayList<>();
		int nw[] = getXYFromLatLng(boundingBox.getNorth(), boundingBox.getWest(), zoom);
		int ne[] = getXYFromLatLng(boundingBox.getNorth(), boundingBox.getEast(), zoom);
		int sw[] = getXYFromLatLng(boundingBox.getSouth(), boundingBox.getWest(), zoom);

		int xMin = nw[0];
		int xMax = ne[0];
		int yMin = nw[1];
		int yMax = sw[1];

		for (int x = xMin; x <= xMax; x++){
			for(int y = yMin; y <= yMax; y++){
				quadkeys.add(toQuad(x, y, zoom));
			}
		}
		return quadkeys;
	}

	public static String generateQuadkey(Location latLng, int zoom){
		int[] xy = getXYFromLatLng(latLng.getLat(), latLng.getLng(), zoom);
		return toQuad(xy[0], xy[1], zoom);
	}

	private static int[] getXYFromLatLng(double lat, double lng, int zoom){
		double n = Math.pow(2, zoom);
		int xtile = (int) Math.floor(n * ((lng + 180) / 360));
		int ytile = (int) Math.floor(n * (1 - (Math.log(Math.tan(lat * Math.PI / 180) +
			(1 / Math.cos(lat * Math.PI / 180))) / Math.PI )) / 2);
		return new int[]{xtile, ytile};
	}

	private static String toQuad(int x, int y, int z){
		String quadKey = "";
		for (int i = z; i > 0; i--){
			int bitmask = 1 << (i - 1);
			int digit = 0;
			if ((x & bitmask) != 0){
				digit |= 1;
			}
			if ((y & bitmask) != 0){
				digit |= 2;
			}
			quadKey += digit;
		}
		return quadKey;
	}
}
