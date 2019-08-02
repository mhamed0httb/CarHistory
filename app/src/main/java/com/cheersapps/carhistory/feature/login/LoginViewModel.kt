package com.cheersapps.carhistory.feature.login

import android.arch.lifecycle.ViewModel
import com.cheersapps.carhistory.common.application.App
import javax.inject.Inject

class LoginViewModel @Inject constructor(): ViewModel() {

    init {
        App.appComponent.inject(this)
    }


}