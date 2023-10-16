package com.roberto_product.local.feature.product.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.rob_products.models.Product

@Entity(tableName = ProductEntity.TABLE_NAME)
data class ProductEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "product_id")
    val productId: Long,

    @ColumnInfo(name = "product_name")
    val productName: String,

    @ColumnInfo(name = "product_category")
    val productCategory: String,

    @ColumnInfo(name = "product_price")
    val productPrice: Int,

    @ColumnInfo(name = "product_bg_color")
    val productBgColor: String,
    @ColumnInfo(name = "product_image")
    val productImage: Int,
) {
    companion object {
        const val TABLE_NAME = "products"
    }
}

fun Product.toEntity(productImage: Int) =
    ProductEntity(
        productId,
        productName,
        productCategory,
        productPrice,
        productBgColor,
        productImage = productImage,
    )

fun ProductEntity.toDomain() =
    Product(
        productId,
        productName,
        productCategory,
        productPrice,
        productBgColor,
        productImage = productImage,
    )
