package com.cheersapps.carhistory.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.cheersapps.carhistory.data.entity.Location

@Dao
interface LocationDao {

    @Insert(onConflict = OnConflictStrategy.FAIL)
    fun insert(location: Location)

    @Insert(onConflict = OnConflictStrategy.FAIL)
    fun insert(locations: List<Location>)

    @Delete
    fun delete(location: Location)

    @Update
    fun update(location: Location)

    @Query("SELECT * FROM Location WHERE id =:locationId LIMIT 1")
    fun findById(locationId: String): LiveData<Location>

    @Query("SELECT * FROM Location")
    fun findAll(): LiveData<List<Location>>

    @Query("DELETE FROM Location")
    fun clearAll()
}