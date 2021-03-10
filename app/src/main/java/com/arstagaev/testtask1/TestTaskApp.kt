package com.arstagaev.testtask1

import android.app.Application
import com.arstagaev.testtask1.storage.PreferenceMaestro
import timber.log.Timber

class TestTaskApp : Application() {

    override fun onCreate() {
        super.onCreate()

        // init SharedPref
        PreferenceMaestro.init(this)
        // init timber
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
            Timber.tag("arsen")
        }

    }
}