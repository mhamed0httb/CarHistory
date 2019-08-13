package com.cheersapps.carhistory.usecase.register

import com.cheersapps.carhistory.core.usecase.BaseUseCaseInOut
import java.util.regex.Pattern

class UsernameValidatorUseCase: BaseUseCaseInOut<String, Boolean>() {


    override fun execute(parameter: String): Boolean {
        val pattern = Pattern.compile("[^\\s]+")
        val matcher = pattern.matcher(parameter)
        return matcher.matches()
    }
}