package com.rob_product_domain.usecase.feature.product

import javax.inject.Inject

class ProductListUseCase @Inject constructor(
    val getListOfProducts: GetListOfProductsUseCase,
    val getListOfProductByCategoryName: GetListOfProductByCategoryUseCase,
    val getDistinctCategory: GetDistinctCategoryListUseCase,
)
