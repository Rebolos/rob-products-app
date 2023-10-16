package com.products.presentation.feature.products.adapter

import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.selection.ItemDetailsLookup
import androidx.recyclerview.selection.SelectionTracker
import com.products.presentation.utils.ProductCategoryCallBack
import com.rob_product_common.base.BaseListAdapter
import com.rob_products_app.R
import com.rob_products_app.databinding.ItemProductCategoryBinding

class ProductCategoryAdapter(private val onProductCategoryClicked: (String, Boolean) -> Unit) :
    BaseListAdapter<
        String,
        BaseListAdapter.BaseViewViewHolder<String>,
        >(
        ProductCategoryCallBack(),
    ) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewViewHolder<String> {
        return ProductCategoryHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_product_category,
                parent,
                false,
            ),
        )
    }

    var tracker: SelectionTracker<String>? = null

    override fun onBindViewHolder(holder: BaseViewViewHolder<String>, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ProductCategoryHolder(override val binding: ItemProductCategoryBinding) :
        BaseViewViewHolder<String>(binding) {

        override fun bind(item: String) {
            binding.productCategoryCardView.isSelected = tracker?.isSelected(item) ?: false
            binding.txtproductCategory.text = item
            if (binding.productCategoryCardView.isSelected) {
                onProductCategoryClicked(item, tracker?.isSelected(item) ?: false)
            }
        }

        fun getItemDetails(): ItemDetailsLookup.ItemDetails<String> =
            object : ItemDetailsLookup.ItemDetails<String>() {
                override fun getPosition(): Int = adapterPosition
                override fun getSelectionKey(): kotlin.String? = getItem(adapterPosition)

                // enable single tap selection
                override fun inSelectionHotspot(e: MotionEvent) = true
            }
    }
}
