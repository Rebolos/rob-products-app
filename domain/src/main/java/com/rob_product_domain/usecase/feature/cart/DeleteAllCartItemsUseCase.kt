package com.rob_product_domain.usecase.feature.cart

import com.rob_product_common.utils.Resource
import com.rob_products_data.feature.cart.source.CartRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class DeleteAllCartItemsUseCase @Inject constructor(private val categoryRepository: CartRepository) {
    operator fun invoke(): Flow<Resource<Unit>> = flow {
        try {
            emit(Resource.Loading())
            categoryRepository.deleteAllCartItems()
            emit(Resource.Success(Unit))
        } catch (e: Exception) {
            emit(Resource.Error(throwable = e, message = e.localizedMessage.orEmpty()))
        } finally {
            emit(Resource.Idle())
        }
    }
}
