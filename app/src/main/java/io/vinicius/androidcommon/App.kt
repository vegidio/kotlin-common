package io.vinicius.androidcommon

import android.app.Application
import io.vinicius.androidcommon.dagger.AppComponent
import io.vinicius.androidcommon.dagger.AppModule
import io.vinicius.androidcommon.dagger.DaggerAppComponent
import timber.log.Timber

class App : Application()
{
    companion object {
        @JvmStatic lateinit var component: AppComponent
    }

    override fun onCreate()
    {
        super.onCreate()

        // Make sure the log is enabled only in debug mode
        if (BuildConfig.DEBUG)
            Timber.plant(Timber.DebugTree())

        // Dagger
        component = DaggerAppComponent.builder()
            .appModule(AppModule(this))
            .build()

        component.inject(this)
    }
}
