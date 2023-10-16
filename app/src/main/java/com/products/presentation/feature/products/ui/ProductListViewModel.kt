package com.products.presentation.feature.products.ui

import androidx.lifecycle.viewModelScope
import com.products.presentation.feature.products.model.ProductUIModel
import com.products.presentation.feature.products.model.toUIModel
import com.products.presentation.feature.products.state.ProductAction
import com.products.presentation.feature.products.state.ProductActionState
import com.products.presentation.feature.products.state.ProductErrorState
import com.products.presentation.feature.products.state.ProductViewState
import com.products.presentation.utils.ProductCategoryType
import com.rob_product_common.base.BaseStateViewModel
import com.rob_product_common.utils.DispatcherProvider
import com.rob_product_common.utils.StateActionHandler
import com.rob_product_common.utils.ViewStateUpdater
import com.rob_product_common.utils.handleResourceFlow
import com.rob_product_common.utils.launchWithTimber
import com.rob_product_domain.usecase.feature.product.ProductListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class ProductListViewModel @Inject constructor(
    private val productUseCase: ProductListUseCase,
    private val dispatchers: DispatcherProvider,
) : BaseStateViewModel<ProductViewState, ProductActionState, ProductErrorState, ProductAction>(),
    ViewStateUpdater<ProductViewState>,
    StateActionHandler<ProductAction> {
    override val initialState: ProductViewState
        get() = ProductViewState()

    init {
        viewModelScope.launchWithTimber {
            try {
                // to run the query in parallel to app improve performance
                launch { getProducts() }
                launch { getDistinctProductCategory() }
                actionFlow.onEach { actionState ->
                    handleAction(actionState)
                }.launchIn(this)
            } catch (e: Exception) {
                showError(e)
                Timber.e(e)
            }
        }
    }

    private suspend fun getProducts() {
        handleResourceFlow(
            resourceFlow = { productUseCase.getListOfProducts() },
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
                    currentState.copy(listOfProducts = result.map { it.toUIModel() })
                }
            },
        )
    }

    override suspend fun handleAction(action: ProductAction) {
        when (action) {
            is ProductAction.ClickProducts -> handleProductClick(action.productsUIModel)
            ProductAction.AddProducts -> {
            }

            is ProductAction.ClickProductCategory -> handleCategoryClick(action)
            ProductAction.RefreshListOfProducts -> handleRefresh()
        }
    }

    private suspend fun handleProductClick(productUIModel: ProductUIModel) {
        mutableActionState.emit(ProductActionState.NavigateToCartDetails(productUIModel))
    }

    private suspend fun handleCategoryClick(action: ProductAction.ClickProductCategory) {
        val categoryName = action.categoryName

        updateState { currentState ->
            currentState.copy(selectedCategory = categoryName)
        }
        if (shouldLoadProductByCategoryName(action.isUserSelectProductCategory, categoryName)) {
            getListOfProductByCategoryName(categoryName)
        } else {
            getProducts()
        }
    }

    private suspend fun handleRefresh() {
        val currentState = mutableViewState.value
        if (currentState.selectedCategory == ProductCategoryType.ALL_X.categoryType) {
            getProducts()
        }
    }

    private fun shouldLoadProductByCategoryName(
        isUserSelectProductCategory: Boolean,
        categoryName: String,
    ): Boolean {
        return isUserSelectProductCategory && categoryName != ProductCategoryType.ALL_X.categoryType
    }

    override fun updateState(updateBlock: (ProductViewState) -> ProductViewState) {
        mutableViewState.update { currentState ->
            updateBlock(currentState)
        }
    }

    private suspend fun showError(throwable: Throwable?) {
        throwable?.let {
            mutableErrorState.emit(ProductErrorState.CommonError(throwable))
        }
    }

    private suspend fun getListOfProductByCategoryName(categoryName: String) {
        handleResourceFlow(
            resourceFlow = { productUseCase.getListOfProductByCategoryName(categoryName = categoryName) },
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
                    currentState.copy(listOfProducts = result.map { it.toUIModel() })
                }
            },
        )
    }

    private suspend fun getDistinctProductCategory() {
        handleResourceFlow(
            resourceFlow = { productUseCase.getDistinctCategory() },
            isLoading = { isLoading ->
                updateState { currentState ->
                    currentState.copy(isLoading = isLoading)
                }
            },
            onError = { throwable ->
                showError(throwable)
            },
            onSuccessResult = { distinctProductCategories ->
                updateState { currentState ->
                    currentState.copy(distinctProductCategories = distinctProductCategories)
                }
            },
        )
    }
}
