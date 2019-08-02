package com.cheersapps.carhistory.usecase.register

import com.cheersapps.carhistory.core.usecase.BaseUseCaseInOut
import com.cheersapps.carhistory.data.entity.User
import com.cheersapps.carhistory.data.repository.user.UserRepository
import io.reactivex.Completable
import javax.inject.Inject

class InsertUserUseCase @Inject constructor() : BaseUseCaseInOut<User, Completable>() {

    @Inject
    lateinit var userRepository: UserRepository

    override fun execute(parameter: User): Completable {
        return userRepository.insertUser(parameter)
    }
}