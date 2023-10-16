package com.rob_product_domain.usecase.feature.cart

import com.rob_product_common.utils.Resource
import com.rob_products_data.feature.cart.source.CartRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class DeleteCartByIdUseCase @Inject constructor(private val cartRepository: CartRepository) {
    operator fun invoke(cartId: Long): Flow<Resource<Unit>> = flow {
        try {
            emit(Resource.Loading())
            val response =
                cartRepository.deleteCartItemById(cartId = cartId)
            emit(Resource.Success(response))
        } catch (e: Exception) {
            emit(Resource.Error(throwable = e, message = e.localizedMessage.orEmpty()))
        } finally {
            emit(Resource.Idle())
        }
    }
}
