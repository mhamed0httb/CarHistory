package com.cheersapps.carhistory.feature.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cheersapps.carhistory.common.application.App
import com.cheersapps.carhistory.common.resource.Resource
import com.cheersapps.carhistory.data.entity.User
import com.cheersapps.carhistory.usecase.login.LoginUseCase
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class LoginViewModel @Inject constructor() : ViewModel() {

    init {
        App.appComponent.inject(this)
    }

    @Inject
    lateinit var loginUseCase: LoginUseCase

    private val loginLiveData = MutableLiveData<Resource<User>>()
    fun observeLogin(): LiveData<Resource<User>> = loginLiveData

    fun login(username: String, password: String) {
        loginUseCase.execute(Pair(username, password))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .delay(5, TimeUnit.SECONDS)
                .doOnSubscribe {
                    loginLiveData.postValue(Resource.loading())
                }
                .subscribe(object : DisposableSingleObserver<User>() {
                    override fun onSuccess(user: User) {
                        loginLiveData.postValue(Resource.success(user))
                    }

                    override fun onError(e: Throwable) {
                        loginLiveData.postValue(Resource.error(e.message!!))
                    }

                })
    }


}