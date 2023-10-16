package com.products.presentation.feature.cart.adapter

import com.products.presentation.feature.cart.model.CartItemUIModel
import com.rob_product_common.widget.DataBindingListAdapter
import com.rob_products_app.BR
import com.rob_products_app.R
import com.rob_products_app.databinding.ItemCartBinding

class CartListAdapter(
    private val onCartClicked: (CartItemUIModel) -> Unit,
    private val onDeleteCart: (CartItemUIModel) -> Unit,
) :
    DataBindingListAdapter<CartItemUIModel, ItemCartBinding>(
        idProvider = { it.id },
        contentChecker = { old, new ->
            old.cartPrice == new.cartPrice &&
                old.cartProductTotalCount == new.cartProductTotalCount &&
                old.color == new.color && old.productName == new.productName
        },
    ) {
    override val bindingItemId: Int
        get() = BR.cart
    override val layoutRes: Int
        get() = R.layout.item_cart

    override fun onBindingCreated(binding: ItemCartBinding) {
        super.onBindingCreated(binding)
        binding.root.setOnClickListener {
            binding.cart?.let {
                onCartClicked(it)
            }
        }
        binding.btnClose.setOnClickListener {
            binding.cart?.let(onDeleteCart)
        }
    }
}
