package com.cheersapps.carhistory.di.component

import com.cheersapps.carhistory.di.module.ApplicationModule
import com.cheersapps.carhistory.di.module.StorageModule
import com.cheersapps.carhistory.feature.home.HomeViewModel
import com.cheersapps.carhistory.feature.login.LoginActivity
import com.cheersapps.carhistory.feature.login.LoginViewModel
import com.cheersapps.carhistory.feature.register.RegisterViewModel
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [ApplicationModule::class, StorageModule::class]
)
interface ApplicationComponent {
    //fun inject(loginActivity: LoginActivity)
    fun inject(loginViewModel: LoginViewModel)
    fun inject(registerViewModel: RegisterViewModel)
    fun inject(homeViewModel: HomeViewModel)
}