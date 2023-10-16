package com.products.presentation.feature.products.state

import com.products.presentation.feature.products.model.ProductUIModel
import com.products.presentation.utils.ProductCategoryType
import com.rob_product_common.utils.RobViewState

data class ProductViewState(
    override val isLoading: Boolean = false,
    val listOfProducts: List<ProductUIModel> = emptyList(),
    val distinctProductCategories: List<String> = emptyList(),
    val selectedCategory: String = ProductCategoryType.ALL_X.categoryType,
) : RobViewState
