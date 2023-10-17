package com.products.presentation.feature.checkout.state

sealed interface CheckoutActionState {

    object InvalidNameError : CheckoutActionState
    object InvalidEmailError : CheckoutActionState
    object CheckoutFieldsError : CheckoutActionState
    object ClearFieldsError: CheckoutActionState
    object CartListIsRequired : CheckoutActionState
    object TotalIsRequired : CheckoutErrorState
    object TermsAndConditionIsRequired : CheckoutActionState
    data class NavigateToOrderConfirmation(val orderId: Long) : CheckoutActionState
}
