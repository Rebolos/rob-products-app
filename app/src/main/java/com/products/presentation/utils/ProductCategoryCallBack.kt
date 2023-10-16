package com.products.presentation.utils

import androidx.recyclerview.widget.DiffUtil

class ProductCategoryCallBack : DiffUtil.ItemCallback<String>() {

    override fun areItemsTheSame(oldItem: String, newItem: String) =
        oldItem == newItem

    override fun areContentsTheSame(
        oldItem: String,
        newItem: String
    ) =
        oldItem == newItem
}