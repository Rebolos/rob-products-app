package com.rob_product_domain.usecase.feature.cart

import android.util.Log
import com.rob_product_common.utils.Resource
import com.rob_products_data.feature.cart.source.CartRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetTotalCountInCartUseCase @Inject constructor(private val cartRepository: CartRepository) {
    operator fun invoke(): Flow<Resource<Int>> = flow {
        try {
            emit(Resource.Loading())
            val response =
                cartRepository.getTotalItemCountInCart()
            Log.d("TEST","Response baii $response")
            emit(Resource.Success(response))
        } catch (e: Exception) {
            emit(Resource.Error(throwable = e, message = e.localizedMessage.orEmpty()))
        } finally {
            emit(Resource.Idle())
        }
    }
}
