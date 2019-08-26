package com.cheersapps.carhistory.feature.home

import androidx.lifecycle.ViewModel
import com.cheersapps.carhistory.common.application.App
import com.cheersapps.carhistory.data.entity.Repair
import com.cheersapps.carhistory.data.repository.repair.RepairRepository
import com.cheersapps.carhistory.usecase.login.GetLoggedInUserUseCase
import com.cheersapps.carhistory.usecase.login.SetStayLoggedIn
import com.cheersapps.carhistory.usecase.profile.CheckEditPasswordFieldsUseCase
import com.cheersapps.carhistory.usecase.profile.FieldsValidator
import io.reactivex.Completable
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

    @Inject
    lateinit var repairRepository: RepairRepository

    fun setStayLoggedIn(stay: Boolean) {
        setStayLoggedIn.execute(stay)
    }

    fun checkEditPasswordFields(oldPass: String, newPass: String, confirmPass: String): Triple<FieldsValidator, FieldsValidator, FieldsValidator> {
        return checkEditPasswordFieldsUseCase.execute(Triple(oldPass, newPass, confirmPass))
    }

    fun getLoggedInUser() = getLoggedInUserUseCase.execute()

    fun insertRepair(repair: Repair): Completable {
        return repairRepository.insert(repair)
    }

}