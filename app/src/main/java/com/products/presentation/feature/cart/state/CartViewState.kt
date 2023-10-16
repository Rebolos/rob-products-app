package com.products.presentation.feature.cart.state

import com.products.presentation.feature.cart.model.CartItemUIModel
import com.rob_product_common.utils.RobViewState

data class CartViewState(
    val cartList: List<CartItemUIModel> = emptyList(),
    override val isLoading: Boolean = false,
    val isShouldCartEmptyState: Boolean = false,
    val cartTotal: Int? = null,
) : RobViewState
