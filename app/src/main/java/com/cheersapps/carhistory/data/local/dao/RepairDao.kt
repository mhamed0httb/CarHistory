package com.cheersapps.carhistory.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.cheersapps.carhistory.data.entity.Repair
import io.reactivex.Single

@Dao
interface RepairDao {

    @Insert(onConflict = OnConflictStrategy.FAIL)
    fun insert(repair: Repair)

    @Insert(onConflict = OnConflictStrategy.FAIL)
    fun insert(repairs: List<Repair>)

    @Delete
    fun delete(repair: Repair)

    @Update
    fun update(repair: Repair)

    @Query("SELECT * FROM Repair WHERE id =:repairId")
    fun findById(repairId: String): LiveData<Repair>

    @Query("SELECT * FROM Repair")
    fun findAll(): LiveData<List<Repair>>

    @Query("DELETE FROM Repair")
    fun clearAll()

    @Query("SELECT COUNT(id) FROM Repair")
    fun count(): Single<Int>

}