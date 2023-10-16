package com.rob_product_domain.usecase.feature.cart

import javax.inject.Inject

data class CartUseCase @Inject constructor(
    val getTotalCountInCart: GetTotalCountInCartUseCase,
    val insertProductToCart: InsertProductToCartUseCase,
    val getAllCartItemCount: GetAllCartItemUseCase,
    val getTotalPriceAddedInCart: CalculateTotalPriceInCartUseCase,
    val deleteCartById: DeleteCartByIdUseCase,
    val deleteAllCartsItem: DeleteAllCartItemsUseCase
)
