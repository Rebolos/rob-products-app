package com.products.presentation.feature.cart.model

import android.content.Context
import android.os.Parcelable
import com.rob_product_common.extensions.parseColor
import com.rob_products.models.CartItem
import com.rob_products_app.R
import kotlinx.parcelize.Parcelize

@Parcelize
data class CartItemUIModel(
    val id: Long,
    val productName: String,
    val cartProductTotalCount: Int,
    val cartPrice: Int,
    val color: String,
) : Parcelable {
    fun getPrice(context: Context): String = context.getString(R.string.cart_total, cartPrice)

    fun getCartBackgroundColor(): Int =
        color.parseColor()
}

fun CartItem.toUIModel() =
    CartItemUIModel(
        id = id ?: 0,
        productName = productName,
        cartProductTotalCount = productCount ?: 0,
        cartPrice = productPrice,
        color = color,
    )

fun CartItemUIModel.toDomain() =
    CartItem(
        id = id ?: 0,
        productName = productName,
        productCount = cartProductTotalCount ?: 0,
        productPrice = cartPrice,
        color = color,
    )
