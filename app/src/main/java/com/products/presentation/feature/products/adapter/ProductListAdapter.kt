package com.products.presentation.feature.products.adapter

import com.products.presentation.feature.products.model.ProductUIModel
import com.rob_product_common.widget.DataBindingListAdapter
import com.rob_products_app.BR
import com.rob_products_app.R
import com.rob_products_app.databinding.ItemProductBinding

class ProductListAdapter(
    private val onProductClicked: (ProductUIModel) -> Unit,
    private val onProductAddedToCart: (ProductUIModel) -> Unit,
) : DataBindingListAdapter<ProductUIModel, ItemProductBinding>(
    idProvider = { it.productId },
    contentChecker = { old, new ->
        old.productBgColor == new.productBgColor && old.productName == new.productName &&
            old.productPrice == new.productPrice && old.productImage == new.productImage
    },
) {
    override val bindingItemId: Int
        get() = BR.products
    override val layoutRes: Int
        get() = R.layout.item_product

    override fun onBindingCreated(binding: ItemProductBinding) {
        super.onBindingCreated(binding)
        binding.root.setOnClickListener {
            binding.products?.let {
                onProductClicked(it)
            }
        }
        binding.btnLayoutAdd.setOnClickListener {
            binding.products?.let { product ->
                onProductAddedToCart(product)
            }
        }
    }
}
