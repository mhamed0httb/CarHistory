package com.cheersapps.carhistory.feature.home

import androidx.lifecycle.ViewModel
import com.cheersapps.carhistory.common.application.App
import com.cheersapps.carhistory.usecase.login.GetLoggedInUserUseCase
import com.cheersapps.carhistory.usecase.login.SetStayLoggedIn
import com.cheersapps.carhistory.usecase.profile.CheckEditPasswordFieldsUseCase
import com.cheersapps.carhistory.usecase.profile.FieldsValidator
import javax.inject.Inject

class HomeViewModel @Inject constructor() : ViewModel() {

    init {
        App.appComponent.inject(this)
    }

    @Inject
    lateinit var setStayLoggedIn: SetStayLoggedIn

    @Inject
    lateinit var checkEditPasswordFieldsUseCase: CheckEditPasswordFieldsUseCase

    @Inject
    lateinit var getLoggedInUserUseCase: GetLoggedInUserUseCase

    fun setStayLoggedIn(stay: Boolean) {
        setStayLoggedIn.execute(stay)
    }

    fun checkEditPasswordFields(oldPass: String, newPass: String, confirmPass: String): Triple<FieldsValidator, FieldsValidator, FieldsValidator> {
        return checkEditPasswordFieldsUseCase.execute(Triple(oldPass, newPass, confirmPass))
    }

    fun getLoggedInUser() = getLoggedInUserUseCase.execute()

}