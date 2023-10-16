package com.products.presentation.utils

enum class ProductCategoryType(val categoryType: String) {
    ALL_X("All X"),
    JACKETS("Jackets"),
    BLAZERS("Blazers"),
    TEES("Tees"),
}

fun getProductCategoryType(categoryType: String): ProductCategoryType? {
    return ProductCategoryType.values().find { it.categoryType == categoryType }
}
