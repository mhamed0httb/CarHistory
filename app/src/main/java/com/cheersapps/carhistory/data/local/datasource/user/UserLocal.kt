package com.cheersapps.carhistory.data.local.datasource.user

import androidx.lifecycle.LiveData
import com.cheersapps.carhistory.data.entity.Credentials
import com.cheersapps.carhistory.data.entity.User
import com.cheersapps.carhistory.data.local.db.AppDatabase
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject

class UserLocal @Inject constructor() {

    @Inject
    lateinit var appDatabase: AppDatabase

    fun insertUser(user: User): Completable {
        return Completable.fromAction {
            appDatabase.userDao.insert(user)
        }
    }

    fun login(credentials: Credentials): Single<User> {
        return appDatabase.userDao.login(credentials)
    }

    fun findById(id: String): LiveData<User> {
        return appDatabase.userDao.findById(id)
    }

    fun updateUser(user: User): Completable {
        return Completable.fromAction {
            appDatabase.userDao.update(user)
        }
    }

}