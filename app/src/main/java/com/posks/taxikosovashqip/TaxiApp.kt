package com.posks.taxikosovashqip

import android.app.Application
import com.google.firebase.database.FirebaseDatabase

class TaxiApp : Application() {

    override fun onCreate() {
        super.onCreate()
        FirebaseDatabase.getInstance().setPersistenceEnabled(true)
    }
}