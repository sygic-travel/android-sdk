package com.sygic.travel.sdkdemo.utils

import android.content.Context
import android.content.res.Configuration
import android.content.res.Resources
import android.support.v4.content.ContextCompat
import android.util.DisplayMetrics
import android.view.WindowManager
import com.sygic.travel.sdkdemo.R

object Utils {

    val PHOTO_SIZE_PLACEHOLDER = "{size}"

    fun isLandscape(resources: Resources): Boolean {
        return resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
    }

    fun getPhotoUrl(context: Context, urlTemplate: String): String {
        return urlTemplate.replace(PHOTO_SIZE_PLACEHOLDER, getDetailPhotoSize(context))
    }

    fun getDetailPhotoSize(context: Context): String {
        val width: Int
        val height: Int
        val displayMetrics = DisplayMetrics()
        val wm = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val display = wm.defaultDisplay
        display.getMetrics(displayMetrics)

        if (Utils.isLandscape(context.resources)) {
            width = (displayMetrics.widthPixels * 0.6f).toInt()
            height = displayMetrics.heightPixels
        } else {
            width = displayMetrics.widthPixels
            height = width
        }

        return width.toString() + "x" + height
    }

    fun getMarkerHue(category: String): Float {
        when (category) {
            "sightseeing" -> return 14f
            "shopping" -> return 40f
            "eating" -> return 17f
            "discovering" -> return 219f
            "playing" -> return 193f
            "traveling" -> return 224f
            "going_out" -> return 335f
            "hiking" -> return 27f
            "sports" -> return 132f
            "relaxing" -> return 263f
            else -> return 14f
        }
    }

    fun getMarkerColor(context: Context, category: String): Int {
        when (category) {
            "sightseeing" -> return ContextCompat.getColor(context, R.color.marker_sightseeing)
            "shopping" -> return ContextCompat.getColor(context, R.color.marker_shopping)
            "eating" -> return ContextCompat.getColor(context, R.color.marker_eating)
            "discovering" -> return ContextCompat.getColor(context, R.color.marker_discovering)
            "playing" -> return ContextCompat.getColor(context, R.color.marker_playing)
            "traveling" -> return ContextCompat.getColor(context, R.color.marker_traveling)
            "going_out" -> return ContextCompat.getColor(context, R.color.marker_going_out)
            "hiking" -> return ContextCompat.getColor(context, R.color.marker_hiking)
            "sports" -> return ContextCompat.getColor(context, R.color.marker_sports)
            "relaxing" -> return ContextCompat.getColor(context, R.color.marker_relaxing)
            else -> return ContextCompat.getColor(context, R.color.colorPrimary)
        }
    }
}
