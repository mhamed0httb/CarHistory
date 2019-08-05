package com.cheersapps.carhistory.data.repository.user

import com.cheersapps.carhistory.data.entity.Credentials
import com.cheersapps.carhistory.data.entity.User
import com.cheersapps.carhistory.data.local.datasource.user.UserLocal
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject

class UserRepository @Inject constructor() {

    @Inject
    lateinit var userLocal: UserLocal

    fun insertUser(user: User): Completable {
        return userLocal.insertUser(user)
    }

    fun login(credentials: Credentials): Single<User> {
        return userLocal.login(credentials)
    }
}