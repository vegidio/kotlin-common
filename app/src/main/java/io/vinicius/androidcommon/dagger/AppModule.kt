package io.vinicius.androidcommon.dagger

import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides
import io.vinicius.androidcommon.service.ServiceFactory
import javax.inject.Singleton

@Module
class AppModule(private val app: Application)
{
    @Provides
    @Singleton
    fun provideContext(): Context = app

    @Provides
    @Singleton
    fun provideServiceFactory(context: Context): ServiceFactory = ServiceFactory(context)
}
