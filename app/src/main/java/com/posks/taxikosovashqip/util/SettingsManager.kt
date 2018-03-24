package com.posks.taxikosovashqip.util

import android.app.Application
import android.content.Context
import android.content.SharedPreferences

class SettingsManager(application: Application) {

    private val miscellaneousSharedPrefs: SharedPreferences

    val isFirstAppRun: Boolean
        get() {
            val firstAppRun = miscellaneousSharedPrefs.getBoolean(
                    MiscellaneousSharedPrefKeys.FIRST_APP_RUN,
                    true
            )
            if (firstAppRun) {
                miscellaneousSharedPrefs.edit().putBoolean(
                        MiscellaneousSharedPrefKeys.FIRST_APP_RUN,
                        false
                ).apply()
            }
            return firstAppRun
        }

    var username: String?
        get() = miscellaneousSharedPrefs.getString(
                MiscellaneousSharedPrefKeys.USERNAME,
                null
        )
        set(username) = miscellaneousSharedPrefs.edit().putString(
                MiscellaneousSharedPrefKeys.USERNAME,
                username
        ).apply()

    var avatarId: Int
        get() = miscellaneousSharedPrefs.getInt(
                MiscellaneousSharedPrefKeys.AVATAR_ID,
                0
        )
        set(avatarId) = miscellaneousSharedPrefs.edit().putInt(
                MiscellaneousSharedPrefKeys.AVATAR_ID,
                avatarId
        ).apply()

    var languageLocale: String?
        get() = miscellaneousSharedPrefs.getString(
                MiscellaneousSharedPrefKeys.LANGUAGE_LOCALE,
                "en"
        )
        set(languageLocale) = miscellaneousSharedPrefs.edit().putString(
                MiscellaneousSharedPrefKeys.LANGUAGE_LOCALE,
                languageLocale
        ).apply()

    init {
        miscellaneousSharedPrefs = application.getSharedPreferences(
                MISCELLANEOUS_SHARED_PREFS_ID,
                Context.MODE_PRIVATE
        )
    }

    fun clear() {
        miscellaneousSharedPrefs.edit().clear().apply()
    }

    private object MiscellaneousSharedPrefKeys {
        internal const val FIRST_APP_RUN = "first_app_run"
        internal const val USERNAME = "username"
        internal const val AVATAR_ID = "avatar_id"
        internal const val LANGUAGE_LOCALE = "language_locale"
    }

    companion object {
        private const val MISCELLANEOUS_SHARED_PREFS_ID = "com.posks.taxikosovashqip"
    }
}