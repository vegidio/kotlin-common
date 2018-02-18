package io.vinicius.androidcommon

import android.app.Application
import com.facebook.FacebookSdk
import com.facebook.LoggingBehavior
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

        // Timber logging
        setupTimber()

        // Dagger dependency injection
        setupDagger()

        // Facebook
        setupFacebook()
    }

    /*
     * Private Methods
     */

    private fun setupTimber()
    {
        if(BuildConfig.DEBUG)
            Timber.plant(Timber.DebugTree())
    }

    private fun setupDagger()
    {
        component = DaggerAppComponent.builder()
            .appModule(AppModule(this))
            .build()

        component.inject(this)
    }

    private fun setupFacebook()
    {
        if(BuildConfig.DEBUG) {
            FacebookSdk.setIsDebugEnabled(true)
            FacebookSdk.addLoggingBehavior(LoggingBehavior.INCLUDE_ACCESS_TOKENS)
        }
    }
}
