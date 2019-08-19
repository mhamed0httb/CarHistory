package com.cheersapps.carhistory.usecase.profile

import com.cheersapps.carhistory.core.usecase.BaseUseCaseInOut
import javax.inject.Inject

class CheckEditPasswordFieldsUseCase @Inject constructor() : BaseUseCaseInOut<Triple<String, String, String>, Triple<FieldsValidator, FieldsValidator, FieldsValidator>>() {


    override fun execute(parameter: Triple<String, String, String>): Triple<FieldsValidator, FieldsValidator, FieldsValidator> {
        var result = Triple(FieldsValidator.EMPTY, FieldsValidator.EMPTY, FieldsValidator.EMPTY)

        val oldPass = parameter.first
        val newPass = parameter.second
        val confirmPass = parameter.third

        result = if (oldPass.isEmpty())
            result.copy(first = FieldsValidator.EMPTY)
        else
            result.copy(first = FieldsValidator.NOT_EMPTY)

        result = if (newPass.isEmpty())
            result.copy(second = FieldsValidator.EMPTY)
        else
            result.copy(second = FieldsValidator.NOT_EMPTY)

        result = if (confirmPass.isEmpty())
            result.copy(third = FieldsValidator.EMPTY)
        else
            result.copy(third = FieldsValidator.NOT_EMPTY)

        if (result.second == FieldsValidator.NOT_EMPTY) {
            result = if (newPass == confirmPass)
                result.copy(second = FieldsValidator.MATCH)
            else
                result.copy(second = FieldsValidator.NO_MATCH)
        }
        return result
    }
}

enum class FieldsValidator {
    EMPTY, NOT_EMPTY, NO_MATCH, MATCH
}