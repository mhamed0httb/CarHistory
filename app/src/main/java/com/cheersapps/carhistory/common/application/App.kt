package com.cheersapps.carhistory.common.application

import androidx.multidex.MultiDexApplication
import com.cheersapps.carhistory.di.component.ApplicationComponent
import com.cheersapps.carhistory.di.component.DaggerApplicationComponent
import com.cheersapps.carhistory.di.module.ApplicationModule

class App: MultiDexApplication() {

    companion object {
        /**
         * This is for Dagger injection dependency.
         */
        lateinit var appComponent: ApplicationComponent
    }

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerApplicationComponent
            .builder()
            .applicationModule(ApplicationModule(this))
            .build()
    }

}