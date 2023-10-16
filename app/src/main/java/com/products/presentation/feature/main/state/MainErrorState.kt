package com.products.presentation.feature.main.state

interface MainErrorState {
    data class CommonError(
        val error: Throwable
    ) : MainErrorState
}