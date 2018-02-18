package io.vinicius.androidcommon.dagger

import dagger.Component
import io.vinicius.androidcommon.App
import io.vinicius.androidcommon.view.MainActivity
import io.vinicius.androidcommon.view.authentication.AuthenticationFragment
import io.vinicius.androidcommon.view.country.CountryFragment
import io.vinicius.androidcommon.view.login.LoginFragment
import io.vinicius.androidcommon.viewmodel.AuthenticationViewModel
import javax.inject.Singleton

@Singleton
@Component(modules = [
    AppModule::class,
    UtilsModule::class,
    ViewModelModule::class
])

interface AppComponent
{
    // App
    fun inject(target: App)

    // Activities
    fun inject(target: MainActivity)

    // Fragments
    fun inject(target: AuthenticationFragment)
    fun inject(target: CountryFragment)
    fun inject(target: LoginFragment)

    // View Models
    fun inject(target: AuthenticationViewModel)
}
