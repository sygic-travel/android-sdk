package com.sygic.travel.sdkdemo.utils

import android.content.Context
import android.graphics.*
import android.support.v4.content.ContextCompat
import com.sygic.travel.sdk.geo.spread.SpreadedPlace
import com.sygic.travel.sdkdemo.R

object MarkerBitmapGenerator {

    // Creates a round, coloured bitmap
    fun createMarkerBitmap(context: Context, spreadedPlace: SpreadedPlace): Bitmap? {
        try {
            val markerBitmap: Bitmap
            val canvas: Canvas
            val paint: Paint = Paint()
            val rect: Rect
            val rectF: RectF

            // Default color
            var markerColor = ContextCompat.getColor(context, R.color.st_blue)

            // Marker size equals radius * 2
            val markerSize = spreadedPlace.sizeConfig!!.radius shl 1

            if (spreadedPlace.place!!.categories != null && spreadedPlace.place!!.categories!!.isNotEmpty()) {
                markerColor = Utils.getMarkerColor(context, spreadedPlace.place!!.categories!![0])
            }

            markerBitmap = Bitmap.createBitmap(markerSize, markerSize, Bitmap.Config.ARGB_8888)
            canvas = Canvas(markerBitmap)
            rect = Rect(0, 0, markerSize, markerSize)
            rectF = RectF(rect)

            paint.style = Paint.Style.FILL
            paint.color = markerColor
            canvas.drawRoundRect(rectF, (markerSize shr 1).toFloat(), (markerSize shr 1).toFloat(), paint)

            return markerBitmap
        } catch (exception: Exception) {
            exception.printStackTrace()
            return null
        }

    }
}
