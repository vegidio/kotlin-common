package io.vinicius.androidcommon.dagger

import android.app.Application
import android.content.Context
import com.facebook.CallbackManager
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule(private val app: Application)
{
    @Provides
    @Singleton
    fun provideContext(): Context = app

    @Provides
    @Singleton
    fun provideCallbackManager(): CallbackManager = CallbackManager.Factory.create()
}
