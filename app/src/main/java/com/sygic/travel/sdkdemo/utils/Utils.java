package com.sygic.travel.sdkdemo.utils;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;

/**
 * Created by michal.murin on 28.3.2017.
 */

public class Utils {
	private static final String PHOTO_SIZE_PLACEHOLDER = "{size}";

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
			height = (int) ((width >> 3) * 7f);
		}

		return width + "x" + height;
	}
}
