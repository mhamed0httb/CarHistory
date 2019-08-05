package com.cheersapps.carhistory.utils

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity


class NavigationUtils {


    companion object {
        private const val NAVIGATION_EXTRA_KEY = "navigation_extra_key"
        fun navigateTo(
            context: Context,
            finish: Boolean = false,
            extras: Bundle? = null,
            activity: Class<out AppCompatActivity>
        ) {
            val intent = Intent(context, activity)
            extras?.let {
                intent.putExtra(NAVIGATION_EXTRA_KEY, it)
            }
            context.startActivity(intent)
            if (finish)
                (context as AppCompatActivity).finish()
        }
    }
}