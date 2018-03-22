package com.posks.taxikosovashqip.ui

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.posks.taxikosovashqip.R
import com.posks.taxikosovashqip.SettingsManager
import java.util.*
import kotlin.concurrent.schedule

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val goToPreferences: Boolean
        val delayTimer: Long
        val settingsManager = SettingsManager(application)

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

    private fun continueToNextActivity(goToPreferences: Boolean) {
        if (goToPreferences) {
            startActivity(Intent(this, PreferencesActivity::class.java))
        } else {
            startActivity(Intent(this, MainActivity::class.java))
        }
        finish()
    }
}