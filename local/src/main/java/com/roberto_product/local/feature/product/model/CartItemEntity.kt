package com.roberto_product.local.feature.product.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.rob_products.models.CartItem

@Entity(tableName = CartItemEntity.TABLE_NAME)
data class CartItemEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "cart_item_id")
    val id: Long = 0,

    @ColumnInfo(name = "product_name")
    val productName: String,

    @ColumnInfo(name = "product_count")
    val productCount: Int,
    @ColumnInfo(name = "product_price")
    val productPrice: Int,
    @ColumnInfo(name = "product_color")
    val productColor: String,
) {
    companion object {
        const val TABLE_NAME = "cart_items"
    }
}

fun CartItem.toEntity() =
    CartItemEntity(
        productName = productName,
        productCount = productCount ?: 0,
        productPrice = productPrice,
        productColor = color
    )

fun CartItemEntity.toDomain() =
    CartItem(
        productCount = productCount,
        productName = productName,
        productPrice = productPrice,
        id = id,
        color = productColor
    )
