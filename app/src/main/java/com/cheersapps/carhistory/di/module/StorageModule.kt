package com.cheersapps.carhistory.di.module

import android.content.Context
import com.cheersapps.carhistory.data.local.db.AppDatabase
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
}