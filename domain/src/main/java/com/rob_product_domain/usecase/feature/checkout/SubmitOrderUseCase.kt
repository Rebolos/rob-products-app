package com.rob_product_domain.usecase.feature.checkout

import com.rob_product_common.utils.Resource
import com.rob_products.models.CartItem
import com.rob_products_data.feature.checkout.CheckoutRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SubmitOrderUseCase @Inject constructor(private val checkoutRepository: CheckoutRepository) {
    operator fun invoke(
        cartList: List<CartItem>,
        orderTotalPrice: Int,
    ): Flow<Resource<Pair<String?, Boolean>>> =
        flow {
            try {
                emit(Resource.Loading())
                val response =
                    checkoutRepository.createAndSaveOrder(carts = cartList, total = orderTotalPrice)
                val isCreateOrderSuccessful = response.second
                val orderId = response.first
                if (isCreateOrderSuccessful && orderId?.isNotEmpty() == true) {
                    emit(Resource.Success(Pair(orderId, isCreateOrderSuccessful)))
                }
            } catch (e: Exception) {
                emit(Resource.Error(throwable = e, message = e.localizedMessage.orEmpty()))
            } finally {
                emit(Resource.Idle())
            }
        }
}
