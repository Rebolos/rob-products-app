package com.rob_product_domain.usecase.feature.product

import com.rob_product_common.utils.Resource
import com.rob_products.models.Product
import com.rob_products_data.feature.product.source.ProductRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetListOfProductByCategoryUseCase @Inject constructor(private val productRepository: ProductRepository) {
    operator fun invoke(
        categoryName: String,
    ): Flow<Resource<List<Product>>> = flow {
        try {
            emit(Resource.Loading())
            val response =
                productRepository.getListOfProductByCategoryName(categoryName = categoryName)
            emit(Resource.Success(response))
        } catch (e: Exception) {
            emit(Resource.Error(throwable = e, message = e.localizedMessage.orEmpty()))
        } finally {
            emit(Resource.Idle())
        }
    }
}
