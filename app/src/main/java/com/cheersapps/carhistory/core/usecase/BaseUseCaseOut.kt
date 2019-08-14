package com.cheersapps.carhistory.core.usecase

abstract class BaseUseCaseOut<out OUT> : BaseUseCase() {
    abstract fun execute(): OUT
}