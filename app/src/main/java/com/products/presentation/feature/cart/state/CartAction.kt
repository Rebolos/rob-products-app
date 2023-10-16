package com.products.presentation.feature.cart.state

import com.products.presentation.feature.cart.model.CartItemUIModel
import com.products.presentation.feature.checkout.state.CheckoutAction
import com.rob_product_common.utils.Action

sealed class CartAction : Action {
    data class ClickCart(val cartItemUIModel: CartItemUIModel) : CartAction()
    data class DeleteCart(val cartItemUIModel: CartItemUIModel) : CartAction()
    object RefreshCartItems : CartAction()
    object ClickBuyNow : CartAction()
}
