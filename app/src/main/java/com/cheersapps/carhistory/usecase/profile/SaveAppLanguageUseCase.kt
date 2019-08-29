package com.cheersapps.carhistory.usecase.profile

import com.cheersapps.carhistory.common.constant.Constants.APP_LANGUAGE
import com.cheersapps.carhistory.core.usecase.BaseUseCaseIn
import com.cheersapps.carhistory.data.local.db.SharedPreference
import javax.inject.Inject

class SaveAppLanguageUseCase @Inject constructor(): BaseUseCaseIn<String>() {

    @Inject
    lateinit var sharedPreference: SharedPreference

    override fun execute(input: String) {
        sharedPreference.putString(APP_LANGUAGE, input)
    }
}