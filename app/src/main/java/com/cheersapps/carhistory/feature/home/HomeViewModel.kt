package com.cheersapps.carhistory.feature.home

import androidx.lifecycle.ViewModel
import com.cheersapps.carhistory.common.application.App
import com.cheersapps.carhistory.usecase.login.SetStayLoggedIn
import javax.inject.Inject

class HomeViewModel @Inject constructor() : ViewModel() {

    init {
        App.appComponent.inject(this)
    }

    @Inject
    lateinit var setStayLoggedIn: SetStayLoggedIn

    fun setStayLoggedIn(stay: Boolean) {
        setStayLoggedIn.execute(stay)
    }

}