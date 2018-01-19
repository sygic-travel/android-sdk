package com.sygic.travel.sdk.places.geo.spread

import android.graphics.Point

import com.sygic.travel.sdk.places.model.PlaceInfo

/**
 *
 * Wrapper of [PlaceInfo], which has been already processed by the spread algorithm.
 */
class SpreadedPlace
/**
 *
 * Wrapper of [PlaceInfo], which has been already processed by the spread algorithm.
 * @param place A processed place.
 * *
 * @param canvasCoords Place's `x, y` coordinates on map's canvas on display.
 * *
 * @param sizeConfig Place's [SpreadSizeConfig].
 */
(var place: PlaceInfo?, var canvasCoords: Point?, var sizeConfig: SpreadSizeConfig?)
