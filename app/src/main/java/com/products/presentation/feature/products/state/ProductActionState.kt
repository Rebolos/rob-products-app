package com.products.presentation.feature.products.state

import com.products.presentation.feature.products.model.ProductUIModel

sealed class ProductActionState {
    data class NavigateToCartDetails(val productUIModel: ProductUIModel) : ProductActionState()
}
