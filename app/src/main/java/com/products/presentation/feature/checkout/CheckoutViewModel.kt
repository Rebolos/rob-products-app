package com.products.presentation.feature.checkout

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.products.presentation.feature.cart.model.toDomain
import com.products.presentation.feature.checkout.state.CheckoutAction
import com.products.presentation.feature.checkout.state.CheckoutActionState
import com.products.presentation.feature.checkout.state.CheckoutErrorState
import com.products.presentation.feature.checkout.state.CheckoutViewState
import com.rob_product_common.base.BaseStateViewModel
import com.rob_product_common.utils.StateActionHandler
import com.rob_product_common.utils.ViewStateUpdater
import com.rob_product_common.utils.handleResourceFlow
import com.rob_product_common.utils.launchWithTimber
import com.rob_product_domain.usecase.feature.cart.CartUseCase
import com.rob_product_domain.usecase.feature.checkout.SubmitOrderUseCase
import com.rob_products.models.CartItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class CheckoutViewModel @Inject constructor(
    private val submitOrder: SubmitOrderUseCase,
    private val categoryUseCase: CartUseCase,
    savedStateHandle: SavedStateHandle,
) : BaseStateViewModel<CheckoutViewState, CheckoutActionState, CheckoutErrorState, CheckoutAction>(),
    ViewStateUpdater<CheckoutViewState>,
    StateActionHandler<CheckoutAction> {
    override val initialState: CheckoutViewState
        get() = CheckoutViewState()
    private val args by lazy {
        CheckoutFragmentArgs.fromSavedStateHandle(savedStateHandle)
    }

    init {
        viewModelScope.launchWithTimber {
            updateState { currentState ->
                currentState.copy(price = args.orderTotal)
            }
            actionFlow.onEach { action ->
                handleAction(action)
            }.launchIn(this)
        }
    }

    override suspend fun handleAction(action: CheckoutAction) {
        when (action) {
            is CheckoutAction.SubmitUserOrder -> {
                submitUserOrder()
            }

            is CheckoutAction.Toggle -> {
            }

            is CheckoutAction.UserInputEmail -> {
                updateState { currentState ->
                    currentState.copy(userEmailInput = action.email)
                }
            }

            is CheckoutAction.UserInputName -> {
                updateState { currentState ->
                    currentState.copy(userNameInput = action.name)
                }
            }
        }
    }

    override fun updateState(updateBlock: (CheckoutViewState) -> CheckoutViewState) {
        mutableViewState.update { currentState ->
            updateBlock(currentState)
        }
    }

    private suspend fun submitUserOrder() {
        val currentState = getCurrentState()
        val listOfCarts = args.cartItem?.map { domain -> domain.toDomain() } ?: emptyList()
        val areCheckoutFieldsValid = validateCheckoutFields(
            listOfCarts = listOfCarts,
            userEmailInput = currentState.userEmailInput.orEmpty(),
            nameInput = currentState.userNameInput.orEmpty(),
            isUserAgreeToTermAndCondition = true,
            total = args.orderTotal,
            error = { error ->
                mutableActionState.emit(error)
            },
        )
        if (!areCheckoutFieldsValid) {
            return
        }
        handleResourceFlow(
            resourceFlow = {
                submitOrder(
                    cartList = listOfCarts,
                    orderTotalPrice = args.orderTotal,
                )
            },
            isLoading = { isLoading ->
                updateState { currentState ->
                    currentState.copy(isLoading = isLoading)
                }
            },
            onSuccessResult = { orderSuccess ->
                updateState { currentState ->
                    currentState.copy(orderId = orderSuccess.first?.toLongOrNull())
                }
                viewModelScope.launchWithTimber {
                    deleteAllCartItems()
                }
            },
            onError = { error ->
                showError(error)
            },
        )
    }

    private inline fun validateCheckoutFields(
        userEmailInput: String,
        nameInput: String,
        isUserAgreeToTermAndCondition: Boolean,
        error: (CheckoutActionState) -> Unit,
        listOfCarts: List<CartItem>,
        total: Int,
    ): Boolean {
        val isEmailEmpty = userEmailInput.isEmpty()
        val isNameEmpty = nameInput.isEmpty()
        val isListOfCarts = listOfCarts.isEmpty()
        if (isListOfCarts) {
            error(CheckoutActionState.CartListIsRequired)
            return false
        }
        if (total == 0) {
            error(CheckoutActionState.TotalIsRequired)
            return false
        }
        if (isEmailEmpty && isNameEmpty) {
            error(CheckoutActionState.CheckoutFieldsError)
            return false
        }
        if (isEmailEmpty) {
            error(CheckoutActionState.InvalidEmailError)
            return false
        }
        if (isNameEmpty) {
            error(CheckoutActionState.InvalidNameError)
            return false
        }
        if (!isUserAgreeToTermAndCondition) {
            error(CheckoutActionState.TermsAndConditionIsRequired)
            return false
        }
        return true
    }

    private suspend fun deleteAllCartItems() {
        handleResourceFlow(
            resourceFlow = {
                categoryUseCase.deleteAllCartsItem()
            },
            isLoading = { isLoading ->
                updateState { currentState ->
                    currentState.copy(isLoading = isLoading)
                }
            },
            onSuccessResult = { data ->
                val currentState = getCurrentState()
                currentState.orderId?.let { orderId ->
                    mutableActionState.emit(CheckoutActionState.NavigateToOrderConfirmation(orderId = orderId))
                }
            },
            onError = { error ->
                showError(error)
            },
        )
    }

    private fun getCurrentState() = mutableViewState.value

    private suspend fun showError(throwable: Throwable?) {
        throwable?.let { throwable ->
            mutableErrorState.emit(CheckoutErrorState.CommonError(throwable))
        }
    }
}
