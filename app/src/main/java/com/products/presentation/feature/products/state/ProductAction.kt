package com.products.presentation.feature.products.state

import com.products.presentation.feature.products.model.ProductUIModel
import com.rob_product_common.utils.Action

sealed class ProductAction : Action {

    data class ClickProducts(val productsUIModel: ProductUIModel) : ProductAction()
    object AddProducts : ProductAction()

    data class ClickProductCategory(
        val categoryName: String,
        val isUserSelectProductCategory: Boolean,
    ) : ProductAction()

    object RefreshListOfProducts : ProductAction()
}
