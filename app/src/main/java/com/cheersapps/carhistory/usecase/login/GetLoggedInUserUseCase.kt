package com.cheersapps.carhistory.usecase.login

import androidx.lifecycle.LiveData
import com.cheersapps.carhistory.common.constant.Constants
import com.cheersapps.carhistory.core.usecase.BaseUseCaseOut
import com.cheersapps.carhistory.data.entity.User
import com.cheersapps.carhistory.data.local.db.SharedPreference
import com.cheersapps.carhistory.data.repository.user.UserRepository
import javax.inject.Inject

class GetLoggedInUserUseCase @Inject constructor() : BaseUseCaseOut<LiveData<User>>() {


    @Inject
    lateinit var sharedPreference: SharedPreference

    @Inject
    lateinit var userRepository: UserRepository

    override fun execute(): LiveData<User> {
        val userId = sharedPreference.getString(Constants.LOGGED_IN_USER_ID, "")
        return userRepository.findUserById(userId!!)
    }
}