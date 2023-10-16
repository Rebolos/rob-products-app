package com.rob_product_domain.usecase.feature.cart

import com.rob_product_common.utils.Resource
import com.rob_products_data.feature.cart.source.CartRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CalculateTotalPriceInCartUseCase @Inject constructor(private val cartRepository: CartRepository) {

    operator fun invoke(): Flow<Resource<Int>> = flow {
        try {
            emit(Resource.Loading())
            val total = cartRepository.getTotalPriceAddedInCart()
            emit(Resource.Success(total ?: 0))
        } catch (e: Exception) {
            emit(Resource.Error(throwable = e, message = e.localizedMessage.orEmpty()))
        } finally {
            emit(Resource.Idle())
        }
    }
}
