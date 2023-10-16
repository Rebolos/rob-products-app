package com.rob_product_common.utils

interface ViewStateUpdater<VS : RobViewState> {
    fun updateState(updateBlock: (VS) -> VS)
}
