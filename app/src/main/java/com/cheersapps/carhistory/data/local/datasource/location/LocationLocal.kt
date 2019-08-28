package com.cheersapps.carhistory.data.local.datasource.location

import androidx.lifecycle.LiveData
import com.cheersapps.carhistory.data.entity.Location
import com.cheersapps.carhistory.data.local.db.AppDatabase
import io.reactivex.Completable
import javax.inject.Inject

class LocationLocal @Inject constructor() {

    @Inject
    lateinit var appDatabase: AppDatabase


    fun insert(location: Location): Completable {
        return Completable.fromAction {
            appDatabase.locationDao.insert(location)
        }
    }

    fun findAll(): LiveData<List<Location>> {
        return appDatabase.locationDao.findAll()
    }

    fun delete(location: Location): Completable {
        return Completable.fromAction {
            appDatabase.locationDao.delete(location)
        }
    }
}