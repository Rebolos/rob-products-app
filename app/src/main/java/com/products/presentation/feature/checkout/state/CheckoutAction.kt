package com.products.presentation.feature.checkout.state

import com.rob_product_common.utils.Action

sealed class CheckoutAction : Action {
    data class SubmitUserOrder(val isToggle: Boolean) : CheckoutAction()

    data class UserInputName(val name: String) : CheckoutAction()
    data class UserInputEmail(val email: String) : CheckoutAction()
    data class Toggle(val isCheckoutToggle: Boolean) : CheckoutAction()
}
