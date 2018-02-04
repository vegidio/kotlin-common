package io.vinicius.androidcommon.dagger

import dagger.Module
import dagger.Provides
import io.vinicius.androidcommon.viewmodel.AuthenticationViewModel
import javax.inject.Singleton

@Module
class ViewModelModule
{
    @Provides
    @Singleton
    fun provideAuthenticationViewModel(): AuthenticationViewModel = AuthenticationViewModel()
}