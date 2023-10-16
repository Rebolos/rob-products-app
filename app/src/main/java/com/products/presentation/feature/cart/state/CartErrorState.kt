package com.products.presentation.feature.cart.state

sealed class CartErrorState {
    data class CommonError(val throwable: Throwable) : CartErrorState()
}
