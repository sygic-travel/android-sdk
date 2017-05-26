package com.sygic.travel.sdk.geo.spread;

import android.content.res.Resources;

import com.sygic.travel.sdk.R;
import com.sygic.travel.sdk.model.geo.Bounds;

import java.util.ArrayList;
import java.util.List;

import static com.sygic.travel.sdk.geo.spread.SpreadSizeConfig.BIG;
import static com.sygic.travel.sdk.geo.spread.SpreadSizeConfig.MEDIUM;
import static com.sygic.travel.sdk.geo.spread.SpreadSizeConfig.POPULAR;
import static com.sygic.travel.sdk.geo.spread.SpreadSizeConfig.SMALL;

public class SpreadConfigGenerator {
	private static final double LN2 = 0.6931471805599453;
	private static final int GLOBE_SIZE = 256;

	static List<SpreadSizeConfig> getSpreadSizeConfigs(
		Resources resources,
		Bounds bounds,
		CanvasSize canvasSize
	){
		int zoom = getZoom(bounds, canvasSize);

		List<SpreadSizeConfig> sizeConfigs = new ArrayList<>();
		sizeConfigs.add(new SpreadSizeConfig(
			resources.getDimensionPixelSize(R.dimen.marker_radius_popular),
			resources.getDimensionPixelSize(R.dimen.marker_margin_popular),
			POPULAR,
			true,
			0.3f
		));
		sizeConfigs.add(new SpreadSizeConfig(
			resources.getDimensionPixelSize(R.dimen.marker_radius_big),
			resources.getDimensionPixelSize(R.dimen.marker_margin_big),
			BIG,
			true,
			zoom <= 10 ? 0.001f : 0f
		));
		sizeConfigs.add(new SpreadSizeConfig(
			resources.getDimensionPixelSize(R.dimen.marker_radius_medium),
			resources.getDimensionPixelSize(R.dimen.marker_margin_medium),
			MEDIUM,
			false,
			zoom <= 10 ? 0.001f : 0f
		));
		sizeConfigs.add(new SpreadSizeConfig(
			resources.getDimensionPixelSize(R.dimen.marker_radius_small),
			resources.getDimensionPixelSize(R.dimen.marker_margin_small),
			SMALL,
			false,
			zoom <= 10 ? 0.001f : 0f
		));
		return sizeConfigs;
	}

	private static int getZoom(Bounds bounds, CanvasSize canvasSize){
		float angle = bounds.getEast() - bounds.getWest();
		if (angle < 0) {
			angle += 360;
		}
		return (int) Math.round(Math.log(canvasSize.width * 360 / angle / GLOBE_SIZE) / LN2);
	}
}
