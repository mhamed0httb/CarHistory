package com.cheersapps.carhistory.di.module

import android.content.Context
import com.cheersapps.carhistory.data.local.db.AppDatabase
import com.cheersapps.carhistory.data.local.db.SharedPreference
import com.fasterxml.jackson.databind.ObjectMapper
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class StorageModule {


    @Provides
    @Singleton
    internal fun provideAppDatabase(context: Context): AppDatabase {
        return AppDatabase.getInstance(context)!!
    }

    @Provides
    @Singleton
    internal fun providePreferencesHelper(context: Context): SharedPreference =
            SharedPreference(context, ObjectMapper())

}