package com.sygic.travel.sdk.places.geo.spread

import android.content.res.Resources
import com.sygic.travel.sdk.R
import com.sygic.travel.sdk.places.model.geo.Bounds
import java.util.ArrayList

/**
 * SDK's internal generator of sizes configurations for spreading.
 */
object SpreadConfigGenerator {
	private val LN2 = 0.6931471805599453
	private val GLOBE_SIZE = 256

	/**
	 * Creates a list of wanted sizes configurations.
	 * @param resources Resources needed to get dimensions.
	 * *
	 * @param bounds Map bounds withing which the places are supposed to be spread.
	 * *
	 * @param canvasSize Map canvas (view) size in pixels.
	 * *
	 * @return List of wanted sizes configurations.
	 */
	internal fun getSpreadSizeConfigs(
		resources: Resources,
		bounds: Bounds,
		canvasSize: CanvasSize
	): List<SpreadSizeConfig> {
		val zoom = getZoom(bounds, canvasSize)

		val sizeConfigs = ArrayList<SpreadSizeConfig>()
		sizeConfigs.add(SpreadSizeConfig(
			resources.getDimensionPixelSize(R.dimen.marker_radius_popular),
			resources.getDimensionPixelSize(R.dimen.marker_margin_popular),
			SpreadSizeConfig.POPULAR,
			true,
			0.3f
		))
		sizeConfigs.add(SpreadSizeConfig(
			resources.getDimensionPixelSize(R.dimen.marker_radius_big),
			resources.getDimensionPixelSize(R.dimen.marker_margin_big),
			SpreadSizeConfig.BIG,
			true,
			if (zoom <= 10) 0.001f else 0f
		))
		sizeConfigs.add(SpreadSizeConfig(
			resources.getDimensionPixelSize(R.dimen.marker_radius_medium),
			resources.getDimensionPixelSize(R.dimen.marker_margin_medium),
			SpreadSizeConfig.MEDIUM,
			false,
			if (zoom <= 10) 0.001f else 0f
		))
		sizeConfigs.add(SpreadSizeConfig(
			resources.getDimensionPixelSize(R.dimen.marker_radius_small),
			resources.getDimensionPixelSize(R.dimen.marker_margin_small),
			SpreadSizeConfig.SMALL,
			false,
			if (zoom <= 10) 0.001f else 0f
		))
		return sizeConfigs
	}

	/**
	 * Calculates a zoom level for current bounds and canvas size.
	 * @param bounds Map bounds withing which the places are supposed to be spread.
	 * *
	 * @param canvasSize Map canvas (view) size in pixels.
	 * *
	 * @return Zoom level for current bounds and canvas size.
	 */
	private fun getZoom(bounds: Bounds, canvasSize: CanvasSize): Int {
		var angle = bounds.east - bounds.west
		if (angle < 0) {
			angle += 360f
		}
		return Math.round(Math.log(((canvasSize.width * 360).toFloat() / angle / GLOBE_SIZE.toFloat()).toDouble()) / LN2).toInt()
	}
}
