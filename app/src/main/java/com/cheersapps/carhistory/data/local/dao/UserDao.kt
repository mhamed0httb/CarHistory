package com.cheersapps.carhistory.data.local.dao

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*
import com.cheersapps.carhistory.data.entity.User
import io.reactivex.Single

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.FAIL)
    fun insert(user: User)

    @Insert(onConflict = OnConflictStrategy.FAIL)
    fun insert(peoples: List<User>)

    @Delete
    fun delete(user: User)

    @Update
    fun update(user: User)

    @Query("SELECT * FROM User WHERE id =:userId")
    fun findById(userId: String): LiveData<User>

    @Query("SELECT * FROM User")
    fun findAll(): LiveData<List<User>>

    @Query("DELETE FROM User")
    fun clearAll()

    @Query("SELECT COUNT(id) FROM User")
    fun count(): Single<Int>
}