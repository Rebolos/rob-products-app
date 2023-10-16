package com.products.presentation.feature.main.state

sealed class MainActionState {
    data class ShowSuccessMessage(val productName: String) : MainActionState()
}
