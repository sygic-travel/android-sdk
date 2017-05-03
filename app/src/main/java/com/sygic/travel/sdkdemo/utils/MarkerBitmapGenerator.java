package com.sygic.travel.sdkdemo.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.v4.content.ContextCompat;

import com.sygic.travel.sdk.R;
import com.sygic.travel.sdk.geo.spread.SpreadedPlace;

public class MarkerBitmapGenerator {

	// Creates a round, coloured bitmap
	public static Bitmap createMarkerBitmap(Context context, SpreadedPlace spreadedPlace) {
		try {
			Bitmap markerBitmap;
			Canvas canvas;
			Paint paint;
			Rect rect;
			RectF rectF;

			// Default clor
			int markerColor = ContextCompat.getColor(context, R.color.st_blue);

			// Marker size equals radius * 2
			int markerSize = spreadedPlace.getSizeConfig().getRadius() << 1;

			if(spreadedPlace.getPlace().getCategories() != null && spreadedPlace.getPlace().getCategories().size() > 0){
				markerColor = Utils.getMarkerColor(context, spreadedPlace.getPlace().getCategories().get(0));
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
}
