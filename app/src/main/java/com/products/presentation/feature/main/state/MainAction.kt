package com.products.presentation.feature.main.state

import com.rob_product_common.utils.Action
import com.products.presentation.feature.products.model.ProductUIModel

sealed class MainAction : Action {
    data class ClickAddProductToCart(val productUIModel: ProductUIModel) : MainAction()
    object UserClickCart : MainAction()
    object RefreshCartCount: MainAction()
}
