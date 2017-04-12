package com.sygic.travel.sdkdemo.utils;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.support.v4.content.ContextCompat;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;

import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.sygic.travel.sdkdemo.R;

import static com.sygic.travel.sdkdemo.utils.spread.PlacesSpreader.*;

/**
 * Created by michal.murin on 28.3.2017.
 */

public class Utils {
	public static final String PHOTO_SIZE_PLACEHOLDER = "{size}";

	public static boolean isLandscape(Resources resources){
		return resources.getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;
	}

	public static String getPhotoUrl(Context context, String urlTemplate) {
		return urlTemplate.replace(PHOTO_SIZE_PLACEHOLDER, getDetailPhotoSize(context));
	}

	public static String getDetailPhotoSize(Context context) {
		int width, height;
		DisplayMetrics displayMetrics = new DisplayMetrics();
		WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		Display display = wm.getDefaultDisplay();
		display.getMetrics(displayMetrics);

		if(Utils.isLandscape(context.getResources())){
			width = ((int) (displayMetrics.widthPixels * 0.6f));
			height = displayMetrics.heightPixels;
		} else {
			width = displayMetrics.widthPixels;
			height = width;
//			height = (int) ((width >> 3) * 7f);
		}

		return width + "x" + height;
	}

	public static float getMarkerHue(String category){
		switch(category){
			case "sightseeing":
				return 14;
			case "shopping":
				return 40;
			case "eating":
				return 17;
			case "discovering":
				return 219;
			case "playing":
				return 193;
			case "traveling":
				return 224;
			case "going_out":
				return 335;
			case "hiking":
				return 27;
			case "sports":
				return 132;
			case "relaxing":
				return 263;
			default:
				return BitmapDescriptorFactory.HUE_RED;
		}
	}

	public static int getMarkerColor(Context context, String category){
		switch(category){
			case "sightseeing":
				return ContextCompat.getColor(context, R.color.marker_sightseeing);
			case "shopping":
				return ContextCompat.getColor(context, R.color.marker_shopping);
			case "eating":
				return ContextCompat.getColor(context, R.color.marker_eating);
			case "discovering":
				return ContextCompat.getColor(context, R.color.marker_discovering);
			case "playing":
				return ContextCompat.getColor(context, R.color.marker_playing);
			case "traveling":
				return ContextCompat.getColor(context, R.color.marker_traveling);
			case "going_out":
				return ContextCompat.getColor(context, R.color.marker_going_out);
			case "hiking":
				return ContextCompat.getColor(context, R.color.marker_hiking);
			case "sports":
				return ContextCompat.getColor(context, R.color.marker_sports);
			case "relaxing":
				return ContextCompat.getColor(context, R.color.marker_relaxing);
			default:
				return ContextCompat.getColor(context, R.color.st_blue);
		}
	}

	public static int getMarkerSize(Resources resources, String markerType){
		switch(markerType){
			case POPULAR:
				return resources.getDimensionPixelSize(R.dimen.marker_size_popular);
			case SMALL:
				return resources.getDimensionPixelSize(R.dimen.marker_size_small);
			case MEDIUM:
				return resources.getDimensionPixelSize(R.dimen.marker_size_medium);
			case BIG:
				return resources.getDimensionPixelSize(R.dimen.marker_size_big);
			case INVISIBLE:
				return 0;
			default:
				return resources.getDimensionPixelSize(R.dimen.marker_size_dot);
		}
	}
}
