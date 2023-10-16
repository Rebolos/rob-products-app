package com.rob_product_common.utils

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest

suspend inline fun <reified T : Any> handleResourceFlow(
    resourceFlow: () -> Flow<Resource<T>>,
    crossinline onSuccessResult: suspend (T) -> Unit,
    crossinline isLoading: (Boolean) -> Unit,
    crossinline onError: suspend (Throwable) -> Unit
) {
    resourceFlow().collectLatest { result ->
        when (result) {
            is Resource.Error -> {
                result.throwable?.let { onError(it) }
            }

            is Resource.Idle -> {
                isLoading(false)
            }

            is Resource.Loading -> {
                isLoading(true)
            }

            is Resource.Success -> {
                result.data?.let { onSuccessResult(it) }
            }
        }
    }
}