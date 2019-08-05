package com.cheersapps.carhistory.feature.register

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.util.Log
import com.cheersapps.carhistory.common.application.App
import com.cheersapps.carhistory.common.resource.Resource
import com.cheersapps.carhistory.common.resource.ResourceState
import com.cheersapps.carhistory.data.entity.User
import com.cheersapps.carhistory.usecase.register.InsertUserUseCase
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableCompletableObserver
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class RegisterViewModel @Inject constructor() : ViewModel() {

    init {
        App.appComponent.inject(this)
    }

    @Inject
    lateinit var insertUserUseCase: InsertUserUseCase

    var userToRegister: User = User()

    private val storeUserLiveData = MutableLiveData<Resource<Boolean>>()
    fun observeInsertUser(): LiveData<Resource<Boolean>> = storeUserLiveData

    fun storeUserToDatabase(user: User) {
        insertUserUseCase.execute(user)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .delay(5, TimeUnit.SECONDS)
                .doOnSubscribe {
                    storeUserLiveData.postValue(Resource.loading())
                }
                .subscribe(object : DisposableCompletableObserver() {
                    override fun onComplete() {
                        Log.i("user", "the user: $user is inserted with success")
                        storeUserLiveData.postValue(Resource.success(true))
                    }

                    override fun onError(e: Throwable) {
                        Log.i("user", "onError: $e")
                        storeUserLiveData.postValue(Resource.error(e.message!!))
                    }

                })
    }
}