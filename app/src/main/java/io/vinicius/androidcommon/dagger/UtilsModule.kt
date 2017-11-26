package io.vinicius.androidcommon.dagger

import android.content.Context
import dagger.Module
import dagger.Provides
import io.vinicius.androidcommon.util.FragmentUtil
import io.vinicius.androidcommon.util.LocationUtil
import io.vinicius.androidcommon.util.PreferencesUtil
import io.vinicius.androidcommon.util.UiUtil
import io.vinicius.androidcommon.view.MainActivity
import javax.inject.Singleton

@Module
class UtilsModule
{
    @Provides
    @Singleton
    fun providePreferencesUtil(context: Context): PreferencesUtil = PreferencesUtil(context)

    @Provides
    @Singleton
    fun provideLocationUtil(context: Context): LocationUtil = LocationUtil(context)

    @Provides
    @Singleton
    fun provideUiUtil(context: Context): UiUtil = UiUtil(context)

    @Provides
    @Singleton
    fun provideFragmentUtil(activity: MainActivity): FragmentUtil = FragmentUtil(activity)
}