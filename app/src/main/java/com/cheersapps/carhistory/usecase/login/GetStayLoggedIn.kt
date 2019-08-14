package com.cheersapps.carhistory.usecase.login

import com.cheersapps.carhistory.common.constant.Constants
import com.cheersapps.carhistory.core.usecase.BaseUseCaseOut
import com.cheersapps.carhistory.data.local.db.SharedPreference
import javax.inject.Inject

class GetStayLoggedIn @Inject constructor(): BaseUseCaseOut<Boolean>() {

    @Inject
    lateinit var sharedPreference: SharedPreference

    override fun execute(): Boolean = sharedPreference.getBoolean(Constants.STAY_LOGGED_IN, false)
}