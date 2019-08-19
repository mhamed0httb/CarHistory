package com.cheersapps.carhistory.usecase.login

import com.cheersapps.carhistory.common.constant.Constants.LOGGED_IN_USER_ID
import com.cheersapps.carhistory.core.usecase.BaseUseCaseIn
import com.cheersapps.carhistory.data.local.db.SharedPreference
import javax.inject.Inject

class SetLoggedInUserIdUseCase @Inject constructor(): BaseUseCaseIn<String>() {


    @Inject
    lateinit var sharedPreference: SharedPreference

    override fun execute(input: String) {
        sharedPreference.putString(LOGGED_IN_USER_ID, input)
    }
}