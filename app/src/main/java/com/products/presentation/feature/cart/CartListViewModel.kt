package com.products.presentation.feature.cart

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.products.presentation.feature.cart.model.toUIModel
import com.products.presentation.feature.cart.state.CartAction
import com.products.presentation.feature.cart.state.CartActionState
import com.products.presentation.feature.cart.state.CartErrorState
import com.products.presentation.feature.cart.state.CartViewState
import com.rob_product_common.base.BaseStateViewModel
import com.rob_product_common.utils.StateActionHandler
import com.rob_product_common.utils.ViewStateUpdater
import com.rob_product_common.utils.handleResourceFlow
import com.rob_product_common.utils.launchWithTimber
import com.rob_product_domain.usecase.feature.cart.CartUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartListViewModel @Inject constructor(private val cartUseCase: CartUseCase) :
    BaseStateViewModel<CartViewState, CartActionState, CartErrorState, CartAction>(),
    ViewStateUpdater<CartViewState>,
    StateActionHandler<CartAction> {
    override val initialState: CartViewState
        get() = CartViewState()

    init {
        viewModelScope.launchWithTimber {
            launch { getAllListOfCarts() }
            launch { getAllTotalAddedInCart() }
            actionFlow.onEach { actionState ->
                handleAction(actionState)
            }.launchIn(viewModelScope)
        }
    }

    override suspend fun handleAction(action: CartAction) {
        when (action) {
            is CartAction.ClickCart -> {
            }

            is CartAction.DeleteCart -> {
                deleteCartById(cartId = action.cartItemUIModel.id)
            }

            CartAction.RefreshCartItems -> {
                getAllListOfCarts()
            }

            CartAction.ClickBuyNow -> {
                val currentState = getCurrentState()
                mutableActionState.emit(
                    CartActionState.NavigateToCheckout(
                        cartList = currentState.cartList,
                        totalPrice = currentState.cartTotal ?: 0,
                    ),
                )
            }
        }
    }

    override fun updateState(updateBlock: (CartViewState) -> CartViewState) {
        mutableViewState.update { currentState ->
            updateBlock(currentState)
        }
    }

    private suspend fun getAllListOfCarts() {
        handleResourceFlow(
            resourceFlow = { cartUseCase.getAllCartItemCount() },
            isLoading = { isLoading ->
                updateState { currentState ->
                    currentState.copy(isLoading = isLoading)
                }
            },
            onError = { throwable ->
                showError(throwable)
            },
            onSuccessResult = { result ->
                updateState { currentState ->

                    currentState.copy(
                        cartList = result.map { it.toUIModel() },
                        isShouldCartEmptyState = result.isEmpty(),
                    )
                }
            },
        )
    }

    private suspend fun deleteCartById(cartId: Long) {
        handleResourceFlow(
            resourceFlow = { cartUseCase.deleteCartById(cartId = cartId) },
            isLoading = { isLoading ->
                updateState { currentState ->
                    currentState.copy(isLoading = isLoading)
                }
            },
            onError = { throwable ->
                showError(throwable)
            },
            onSuccessResult = { result ->
                viewModelScope.launchWithTimber {
                    launch { getAllListOfCarts() }
                    launch { getAllTotalAddedInCart() }
                }
                mutableActionState.emit(CartActionState.ShowSuccessDeleteMessage)
            },
        )
    }

    private suspend fun getAllTotalAddedInCart() {
        handleResourceFlow(
            resourceFlow = { cartUseCase.getTotalPriceAddedInCart() },
            isLoading = { isLoading ->
                updateState { currentState ->
                    currentState.copy(isLoading = isLoading)
                }
            },
            onError = { throwable ->
                showError(throwable)
            },
            onSuccessResult = { cartTotal ->
                updateState { currentState ->
                    currentState.copy(cartTotal = cartTotal)
                }
            },
        )
    }

    private suspend fun showError(throwable: Throwable?) {
        throwable?.let {
            mutableErrorState.emit(CartErrorState.CommonError(throwable))
        }
    }

    private fun getCurrentState() = mutableViewState.value
}
