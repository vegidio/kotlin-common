package io.vinicius.androidcommon.dagger

import dagger.Component
import io.vinicius.androidcommon.App
import io.vinicius.androidcommon.view.country.CountryFragment
import javax.inject.Singleton

@Singleton
@Component(modules = [
    AppModule::class,
    UtilsModule::class
])

interface AppComponent
{
    // App
    fun inject(target: App)

    // Fragments
    fun inject(target: CountryFragment)
}
