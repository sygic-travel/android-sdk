package com.sygic.travel.sdkdemo.utils;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.support.v4.content.ContextCompat;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;

import com.sygic.travel.sdk.geo.spread.SpreadSizeConfig;
import com.sygic.travel.sdkdemo.R;

import java.util.ArrayList;
import java.util.List;

import static com.sygic.travel.sdk.geo.spread.SpreadSizeConfig.BIG;
import static com.sygic.travel.sdk.geo.spread.SpreadSizeConfig.MEDIUM;
import static com.sygic.travel.sdk.geo.spread.SpreadSizeConfig.POPULAR;
import static com.sygic.travel.sdk.geo.spread.SpreadSizeConfig.SMALL;

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
		}

		return width + "x" + height;
	}

	public static List<SpreadSizeConfig> getSpreadSizeConfigs(Resources resources){
		List<SpreadSizeConfig> sizeConfigs = new ArrayList<>();
		sizeConfigs.add(new SpreadSizeConfig(
			resources.getDimensionPixelSize(R.dimen.marker_radius_popular),
			resources.getDimensionPixelSize(R.dimen.marker_margin_popular),
			POPULAR, false, 9.75f
		));
		sizeConfigs.add(new SpreadSizeConfig(
			resources.getDimensionPixelSize(R.dimen.marker_radius_big),
			resources.getDimensionPixelSize(R.dimen.marker_margin_big),
			BIG, false, 7.5f
		));
		sizeConfigs.add(new SpreadSizeConfig(
			resources.getDimensionPixelSize(R.dimen.marker_radius_medium),
			resources.getDimensionPixelSize(R.dimen.marker_margin_medium),
			MEDIUM, false, 5f
		));
		sizeConfigs.add(new SpreadSizeConfig(
			resources.getDimensionPixelSize(R.dimen.marker_radius_small),
			resources.getDimensionPixelSize(R.dimen.marker_margin_small),
			SMALL, false, 3f
		));
		return sizeConfigs;
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
				return 14;
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
}
