package com.cheersapps.carhistory.usecase.profile

import com.cheersapps.carhistory.common.constant.Constants
import com.cheersapps.carhistory.core.usecase.BaseUseCaseOut
import com.cheersapps.carhistory.data.entity.AppLanguage
import com.cheersapps.carhistory.data.local.db.SharedPreference
import javax.inject.Inject

class GetAppLanguageUseCase @Inject constructor() : BaseUseCaseOut<AppLanguage>() {

    @Inject
    lateinit var sharedPreference: SharedPreference

    override fun execute(): AppLanguage {
        val code = sharedPreference.getString(Constants.APP_LANGUAGE, AppLanguage.ENGLISH.toString())!!
        return AppLanguage.valueOf(code)
    }
}