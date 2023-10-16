package com.products.presentation.feature.cart.state

import com.products.presentation.feature.cart.model.CartItemUIModel

sealed class CartActionState {
    object ShowSuccessDeleteMessage : CartActionState()
    data class NavigateToCheckout(val cartList: List<CartItemUIModel>, val totalPrice: Int) :
        CartActionState()
}
