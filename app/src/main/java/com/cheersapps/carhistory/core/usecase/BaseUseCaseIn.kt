package com.cheersapps.carhistory.core.usecase

abstract class BaseUseCaseIn<in IN> : BaseUseCase() {
    abstract fun execute(input: IN)
}