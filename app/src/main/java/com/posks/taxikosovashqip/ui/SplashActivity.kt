package com.posks.taxikosovashqip.ui

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.posks.taxikosovashqip.LocaleUtils
import com.posks.taxikosovashqip.R
import com.posks.taxikosovashqip.SettingsManager
import java.util.*
import kotlin.concurrent.schedule

class SplashActivity : AppCompatActivity() {
    private lateinit var localeUtils: LocaleUtils

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val goToPreferences: Boolean
        val delayTimer: Long
        val settingsManager = SettingsManager(application)
        localeUtils = LocaleUtils()

        when {
            settingsManager.languageLocale == LocaleUtils.LOCALE_ALBANIAN.toLanguageTag() -> {
                settingsManager.languageLocale = LocaleUtils.LOCALE_ALBANIAN.toLanguageTag()
                setLanguageAlbanian()
            }
            settingsManager.languageLocale == LocaleUtils.LOCALE_ENGLISH.toLanguageTag() -> {
                settingsManager.languageLocale = LocaleUtils.LOCALE_ENGLISH.toLanguageTag()
                setLanguageEnglish()
            }
            settingsManager.languageLocale == LocaleUtils.LOCALE_GERMAN.toLanguageTag() -> {
                settingsManager.languageLocale = LocaleUtils.LOCALE_GERMAN.toLanguageTag()
                setLanguageGerman()
            }
        }

        if (settingsManager.isFirstAppRun) {
            goToPreferences = true
            delayTimer = 1000
        } else {
            goToPreferences = false
            delayTimer = 500
        }

        Timer(this.javaClass.simpleName, false)
                .schedule(delayTimer) { continueToNextActivity(goToPreferences) }
    }

    private fun setLanguageAlbanian() {
        localeUtils.setLocale(this, LocaleUtils.LOCALE_ALBANIAN)
    }

    private fun setLanguageEnglish() {
        localeUtils.setLocale(this, LocaleUtils.LOCALE_ENGLISH)
    }

    private fun setLanguageGerman() {
        localeUtils.setLocale(this, LocaleUtils.LOCALE_GERMAN)
    }

    private fun continueToNextActivity(goToPreferences: Boolean) {
        if (goToPreferences) {
            startActivity(Intent(this, PreferencesActivity::class.java))
        } else {
            startActivity(Intent(this, MainActivity::class.java))
        }
        finish()
    }
}