package io.vinicius.androidcommon.dagger

import android.content.Context
import dagger.Module
import dagger.Provides
import io.vinicius.androidcommon.service.CountryService
import io.vinicius.androidcommon.service.RestFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

@Module
class ServiceModule
{
    @Provides
    @Singleton
    fun provideRestFactory(context: Context): RestFactory = RestFactory(context)

    @Provides
    @Singleton
    fun provideCountryService(rf: RestFactory): CountryService = rf.create(CountryService::class.java)

    @Provides
    @Named("cached")
    fun provideCachedCountryService(rf: RestFactory): CountryService
        = rf.create(CountryService::class.java, 1, TimeUnit.DAYS)
}