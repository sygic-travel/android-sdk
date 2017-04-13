package com.sygic.travel.sdkdemo.utils.spread;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.v4.content.ContextCompat;

import com.sygic.travel.sdk.model.geo.BoundingBox;
import com.sygic.travel.sdk.model.place.Place;
import com.sygic.travel.sdkdemo.R;

import static com.sygic.travel.sdkdemo.utils.Utils.getMarkerColor;

/**
 * Created by michal.murin on 6.4.2017.
 */

public class MarkerProcessor {
	private Resources resources;

	public MarkerProcessor(Resources resources, int mapWidth, int mapHeight) {
		this.resources = resources;

		init(mapWidth, mapHeight);
	}

	private void init(int mapWidth, int mapHeight) {
	}

	public Bitmap createMarkerBitmap(Context context, Place place) {
		try {
			Bitmap markerBitmap;
			Canvas canvas;
			Paint paint;
			Rect rect;
			RectF rectF;

			int markerColor = ContextCompat.getColor(context, R.color.st_blue);
			int markerSize = resources.getDimensionPixelSize(R.dimen.marker_size_dot);

			if(place.getCategories() != null && place.getCategories().size() > 0){
				markerColor = getMarkerColor(context, place.getCategories().get(0));
			}

			if(place.getMarker() != null && !place.getMarker().equals("")){
			}

			if(markerSize == 0){
				return null;
			}

			markerBitmap = Bitmap.createBitmap(markerSize, markerSize, Bitmap.Config.ARGB_8888);
			canvas = new Canvas(markerBitmap);
			paint = new Paint();
			rect = new Rect(0, 0, markerSize, markerSize);
			rectF = new RectF(rect);

			paint.setStyle(Paint.Style.FILL);
			paint.setColor(markerColor);
			canvas.drawRoundRect(rectF, markerSize >> 1, markerSize >> 1, paint);

			return markerBitmap;
		} catch (Exception exception){
			exception.printStackTrace();
			return null;
		}
	}

	public void setNewMapPosition(BoundingBox boundingBox, int zoom) {
	}
}
