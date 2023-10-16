package com.products.presentation.feature.products.model

import android.content.Context
import android.os.Parcelable
import androidx.core.content.ContextCompat
import com.rob_product_common.extensions.parseColor
import com.rob_products.models.Product
import kotlinx.parcelize.Parcelize

@Parcelize
data class ProductUIModel(
    val productId: Long,
    val productName: String,
    val productCategory: String,
    val productPrice: Int,
    val productBgColor: String,
    val productImage: Int,
) : Parcelable {

    fun getBackgroundColor() = productBgColor.parseColor()

    fun getProductImage(context: Context) =
        ContextCompat.getDrawable(context, productImage)

    fun getProductPriceAsString(): String {
        return productPrice.toString()
    }
}

fun Product.toUIModel(): ProductUIModel =
    ProductUIModel(
        productId = productId,
        productName = productName,
        productCategory = productCategory,
        productPrice = productPrice ?: 0,
        productBgColor = productBgColor,
        productImage = productImage ?: 0,
    )
