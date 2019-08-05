package com.cheersapps.carhistory.usecase.login

import com.cheersapps.carhistory.core.usecase.BaseUseCaseInOut
import com.cheersapps.carhistory.data.entity.Credentials
import com.cheersapps.carhistory.data.entity.User
import com.cheersapps.carhistory.data.repository.user.UserRepository
import io.reactivex.Single
import javax.inject.Inject

class LoginUseCase @Inject constructor() : BaseUseCaseInOut<Pair<String, String>, Single<User>>() {

    @Inject
    lateinit var userRepository: UserRepository

    override fun execute(parameter: Pair<String, String>): Single<User> {
        val credential = Credentials(parameter.first, parameter.second)
        return userRepository.login(credential)
    }
}