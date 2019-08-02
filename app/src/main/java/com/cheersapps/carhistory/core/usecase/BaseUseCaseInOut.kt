package com.cheersapps.carhistory.core.usecase

abstract class BaseUseCaseInOut<in IN, out OUT>: BaseUseCase() {
    abstract fun execute(parameter: IN): OUT
}