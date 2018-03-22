package com.posks.taxikosovashqip.util

import android.content.Context
import java.util.*

@Suppress("DEPRECATION")
class LocaleUtils {

    companion object {
        val LOCALE_ALBANIAN = Locale("sq")
        val LOCALE_ENGLISH = Locale.ENGLISH!!
        val LOCALE_GERMAN = Locale.GERMAN!!
    }

    fun setLocale(context: Context, newLocale: Locale) {
        Locale.setDefault(newLocale)

        val resources = context.resources

        val configuration = resources.configuration
        configuration.locale = newLocale

        resources.updateConfiguration(configuration, resources.displayMetrics)
    }
}