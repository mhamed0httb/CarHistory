package com.cheersapps.carhistory.data.repository.location

import androidx.lifecycle.LiveData
import com.cheersapps.carhistory.data.entity.Location
import com.cheersapps.carhistory.data.local.datasource.location.LocationLocal
import io.reactivex.Completable
import javax.inject.Inject

class LocationRepository @Inject constructor() {

    @Inject
    lateinit var locationLocal: LocationLocal

    fun insert(location: Location): Completable {
        return locationLocal.insert(location)
    }

    fun findAll(): LiveData<List<Location>> {
        return locationLocal.findAll()
    }

    fun delete(location: Location): Completable {
        return locationLocal.delete(location)
    }
}