package com.cheersapps.carhistory.usecase.login

import com.cheersapps.carhistory.common.constant.Constants.STAY_LOGGED_IN
import com.cheersapps.carhistory.core.usecase.BaseUseCaseIn
import com.cheersapps.carhistory.data.local.db.SharedPreference
import javax.inject.Inject

class SetStayLoggedIn @Inject constructor(): BaseUseCaseIn<Boolean>() {

    @Inject
    lateinit var sharedPreference: SharedPreference

    override fun execute(input: Boolean) {
        sharedPreference.putBoolean(STAY_LOGGED_IN, input)
    }
}