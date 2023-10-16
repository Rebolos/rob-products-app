package com.products.presentation.feature.main.state

import com.rob_product_common.utils.RobViewState

data class MainViewState(
    val cartTotalCount: Int? = null,
    override val isLoading: Boolean = false,
    val isShouldShowCart: Boolean = false,
) :
    RobViewState
