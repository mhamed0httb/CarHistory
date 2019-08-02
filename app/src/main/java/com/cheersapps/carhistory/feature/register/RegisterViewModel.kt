package com.cheersapps.carhistory.feature.register

import android.arch.lifecycle.ViewModel
import com.cheersapps.carhistory.common.application.App
import com.cheersapps.carhistory.data.entity.User
import com.cheersapps.carhistory.usecase.register.InsertUserUseCase
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class RegisterViewModel @Inject constructor() : ViewModel() {

    init {
        App.appComponent.inject(this)
    }

    @Inject
    lateinit var insertUserUseCase: InsertUserUseCase

    var userToRegister: User = User()

    fun storeUserToDatabase(user: User): Completable {
        return insertUserUseCase.execute(user)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())

    }
}