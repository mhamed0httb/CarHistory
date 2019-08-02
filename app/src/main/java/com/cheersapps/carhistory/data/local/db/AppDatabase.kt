package com.cheersapps.carhistory.data.local.db

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters
import android.content.Context
import com.cheersapps.carhistory.data.entity.User
import com.cheersapps.carhistory.data.local.converter.Converter
import com.cheersapps.carhistory.data.local.dao.UserDao

@Database(entities = [User::class], version = AppDatabase.VERSION, exportSchema = false)
@TypeConverters(Converter::class)
abstract class AppDatabase: RoomDatabase() {

    abstract val userDao: UserDao

    companion object {
        const val VERSION = 2
        private const val NAME = "carHistory.db"
        private var instance: AppDatabase? = null
        private val lock = Any()

        fun getInstance(context: Context): AppDatabase? {
            if (instance == null) {
                synchronized(lock) {
                    if (instance == null) {
                        instance = Room.databaseBuilder(
                            context.applicationContext,
                            AppDatabase::class.java,
                            NAME
                        ).fallbackToDestructiveMigration().build()
                    }
                    return instance
                }
            }
            return instance
        }
    }
}