package com.products.presentation.feature.main

import androidx.lifecycle.viewModelScope
import com.products.presentation.feature.main.state.MainAction
import com.products.presentation.feature.main.state.MainActionState
import com.products.presentation.feature.main.state.MainErrorState
import com.products.presentation.feature.main.state.MainViewState
import com.products.presentation.feature.products.model.ProductUIModel
import com.rob_product_common.base.BaseStateViewModel
import com.rob_product_common.utils.StateActionHandler
import com.rob_product_common.utils.ViewStateUpdater
import com.rob_product_common.utils.handleResourceFlow
import com.rob_product_common.utils.launchWithTimber
import com.rob_product_domain.usecase.feature.cart.CartUseCase
import com.rob_products.models.CartItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val cartUseCase: CartUseCase) :
    BaseStateViewModel<MainViewState, MainActionState, MainErrorState, MainAction>(),
    StateActionHandler<MainAction>,
    ViewStateUpdater<MainViewState> {

    override val initialState: MainViewState
        get() = MainViewState()

    init {
        viewModelScope.launchWithTimber {
            launch { getAllCartItemsCount() }
            actionFlow.onEach { action ->
                handleAction(action)
            }.launchIn(this)
        }
    }

    override suspend fun handleAction(action: MainAction) {
        when (action) {
            is MainAction.ClickAddProductToCart -> {
                addToProductCart(productUIModel = action.productUIModel)
            }

            MainAction.UserClickCart -> {
            }

            MainAction.RefreshCartCount -> {
                getAllCartItemsCount()
            }
        }
    }

    override fun updateState(updateBlock: (MainViewState) -> MainViewState) {
        mutableViewState.update { currentState ->
            updateBlock(currentState)
        }
    }

    private suspend fun getAllCartItemsCount() {
        handleResourceFlow(
            resourceFlow = { cartUseCase.getAllCartItemCount() },
            onSuccessResult = { currentTotalCountInCart ->

                updateState { currentState ->
                    currentState.copy(
                        cartTotalCount = currentTotalCountInCart.size,
                        isShouldShowCart = currentTotalCountInCart.isNotEmpty(),
                    )
                }
            },
            isLoading = { isLoading ->
                updateState { currentState ->
                    currentState.copy(isLoading = isLoading)
                }
            },
            onError = { throwable ->
                showError(throwable)
            },
        )
    }

    private suspend fun addToProductCart(productUIModel: ProductUIModel) {
        with(productUIModel) {
            handleResourceFlow(
                resourceFlow = {
                    cartUseCase.insertProductToCart(
                        CartItem(
                            productName = productName,
                            productPrice = productUIModel.productPrice.toInt(),
                            color = productUIModel.productBgColor,
                        ),
                    )
                },
                onSuccessResult = { currentTotalCountInCart ->
                    // To ensure that the coroutine runs on a different scope,
                    // start a new coroutine using viewModelScope to avoid exception
                    viewModelScope.launchWithTimber {
                        getAllCartItemsCount()
                    }

                    showSuccessMessage(productName = productName)
                },
                isLoading = { isLoading ->
                    updateLoadingState(isLoading)
                },
                onError = { throwable ->
                    showError(throwable)
                },
            )
        }
    }

    private suspend fun showError(throwable: Throwable?) {
        throwable?.let {
            mutableErrorState.emit(MainErrorState.CommonError(throwable))
        }
    }

    private fun updateLoadingState(isLoading: Boolean) {
        updateState { currentState ->
            currentState.copy(isLoading = isLoading)
        }
    }

    private suspend fun showSuccessMessage(productName: String) {
        mutableActionState.emit(MainActionState.ShowSuccessMessage(productName = productName))
    }
}
