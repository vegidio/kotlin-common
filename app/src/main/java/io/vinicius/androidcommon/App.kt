package io.vinicius.androidcommon

import android.app.Activity
import android.app.Application
import timber.log.Timber

class App : Application()
{
    companion object {
        lateinit var activity: Activity
    }

    override fun onCreate()
    {
        super.onCreate()

        // Make sure the log is enabled only in debug mode
        if (BuildConfig.DEBUG)
            Timber.plant(Timber.DebugTree())
    }
}