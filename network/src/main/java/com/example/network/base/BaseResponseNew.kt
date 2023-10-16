package com.example.network.base

import com.google.gson.annotations.SerializedName

data class BaseResponseNew<T>(
    @SerializedName("products") val data: T,
)