package com.rob_product_common.utils

interface StateActionHandler<AS : Action> {
    suspend fun handleAction(action: AS)
}
