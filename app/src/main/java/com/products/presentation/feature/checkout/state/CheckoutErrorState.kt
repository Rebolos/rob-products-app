package com.products.presentation.feature.checkout.state

import com.products.presentation.feature.products.state.ProductErrorState

interface CheckoutErrorState {
    data class CommonError(
        val error: Throwable,
    ) : CheckoutErrorState
}
