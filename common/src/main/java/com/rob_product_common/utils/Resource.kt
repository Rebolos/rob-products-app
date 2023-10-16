package com.rob_product_common.utils

sealed class Resource<T>(
    val data: T? = null,
    val throwable: Throwable? = null,
    val message: String? = null
) {
    class Success<T>(data: T?) : Resource<T>(data = data)
    class Error<T>(throwable: Throwable?, data: T? = null, message: String?) :
        Resource<T>(data = data, throwable = throwable, message)

    class Loading<T>(data: T? = null) : Resource<T>(data)
    class Idle<T>(data: T? = null) : Resource<T>(data)
}
