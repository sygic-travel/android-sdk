package com.sygic.travel.sdkdemo.utils;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;

import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.sygic.travel.sdkdemo.R;

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

	public static int getMarkerColor(String category){
		switch(category){
			case "sightseeing":
				return 0xF6746C;
			case "shopping":
				return 0xE7A41C;
			case "eating":
				return 0xF6936C;
			case "discovering":
				return 0x898F9A;
			case "playing":
				return 0x6CD8F6;
			case "traveling":
				return 0x6B91F6;
			case "going_out":
				return 0xE76CA0;
			case "hiking":
				return 0xD59B6B;
			case "sports":
				return 0x68B277;
			case "relaxing":
				return 0xA06CF6;
			default:
				return 0x348ce3;
		}
	}
}
