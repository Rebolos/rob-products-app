package com.products.presentation.utils

import androidx.recyclerview.selection.ItemKeyProvider
import com.products.presentation.feature.products.adapter.ProductCategoryAdapter

class ProductCategoryKeyProvider(private val adapter: ProductCategoryAdapter) :
    ItemKeyProvider<String>(SCOPE_CACHED) {

    override fun getKey(position: Int): String? =
        adapter.currentList[position]

    override fun getPosition(key: String): Int =
        adapter.currentList.indexOfFirst { it == key }
}
