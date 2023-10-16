package com.products.presentation.feature.checkout.state

import com.rob_product_common.utils.RobViewState
import com.rob_products.models.CartItem

data class CheckoutViewState(
    val cartList: List<CartItem> = emptyList(),
    val price: Int? = null,
    override val isLoading: Boolean = false,
    val userNameInput: String? = null,
    val userEmailInput: String? = null,
    val orderId: Long? = null,

) : RobViewState
