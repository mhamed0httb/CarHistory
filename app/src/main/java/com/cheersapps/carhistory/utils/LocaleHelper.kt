package com.cheersapps.carhistory.utils

import android.annotation.TargetApi
import android.content.Context
import android.os.Build
import java.util.*

object LocaleHelper {

    private val SELECTED_LANGUAGE = "Locale.Helper.Selected.AppLanguage"

    fun setLocale(context: Context, language: String): Context {
        //persist(context, language);

        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            updateResources(context, language)
        } else updateResourcesLegacy(context, language)
    }

    @TargetApi(Build.VERSION_CODES.N)
    private fun updateResources(context: Context, language: String): Context {
        val locale = Locale(language)
        Locale.setDefault(locale)

        val configuration = context.resources.configuration
        configuration.setLocale(locale)

        context.resources.updateConfiguration(configuration, context.resources.displayMetrics) // added by me

        return context.createConfigurationContext(configuration)
    }

    private fun updateResourcesLegacy(context: Context, language: String): Context {
        val locale = Locale(language)
        Locale.setDefault(locale)

        val resources = context.resources

        val configuration = resources.configuration
        configuration.locale = locale

        resources.updateConfiguration(configuration, resources.displayMetrics)

        return context
    }


}