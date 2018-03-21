package com.posks.taxikosovashqip.ui

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.posks.taxikosovashqip.R
import java.util.*
import kotlin.concurrent.schedule

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        Timer(this.javaClass.simpleName, false)
                .schedule(500) { continueToNextActivity() }
    }

    private fun continueToNextActivity() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
}