package com.products.presentation.feature.products.state

interface ProductErrorState {
    data class CommonError(
        val error: Throwable
    ) : ProductErrorState
}