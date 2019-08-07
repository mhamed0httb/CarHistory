package com.cheersapps.carhistory.utils

import android.content.Context
import android.content.res.Resources
import android.util.DisplayMetrics
import android.view.WindowManager

object ScreenUtils {

    fun getDensityName(context: Context): String {

        val density = context.resources.displayMetrics.density

        if (density >= 4.0) {
            return "xxxhdpi"
        }
        if (density >= 3.0) {
            return "xxhdpi"
        }
        if (density >= 2.0) {
            return "xhdpi"
        }
        if (density >= 1.5) {
            return "hdpi"
        }
        return if (density >= 1.0) {
            "mdpi"
        } else "ldpi"
    }

    fun getScreenHeightInPixels(context: Context?): Int {
        return if (context != null) {
            val wm = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
            val metrics = DisplayMetrics()
            wm.defaultDisplay.getMetrics(metrics)
            metrics.heightPixels
        } else {
            0
        }
    }

    fun getScreenWidthInPixels(context: Context?): Int {
        return if (context != null) {
            val wm = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
            val metrics = DisplayMetrics()
            wm.defaultDisplay.getMetrics(metrics)
            metrics.widthPixels
        } else {
            0
        }
    }

    fun getStatusBarHeight(resources: Resources): Int {
        var result = 0
        val resourceId = resources.getIdentifier("status_bar_height", "dimen", "android")
        if (resourceId > 0) {
            result = resources.getDimensionPixelSize(resourceId)
        }
        return result
    }

    fun getAndroidActionBarSize(context: Context?): Int {
        val styledAttributes = context!!.theme.obtainStyledAttributes(
                intArrayOf(android.R.attr.actionBarSize))
        return styledAttributes.getDimension(0, 0f).toInt()
    }

    fun convertDpToPixel(dp: Float, context: Context): Float {
        return dp * (context.resources.displayMetrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)
    }



    fun actionBarToPixel(context: Context): Int {
        val styledAttributes = context.getTheme().obtainStyledAttributes(
                intArrayOf(android.R.attr.actionBarSize)
        )
        return styledAttributes.getDimension(0, 0f).toInt()
    }

}