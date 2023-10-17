package com.products.presentation.feature.products.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.selection.SelectionPredicates
import androidx.recyclerview.selection.StorageStrategy
import androidx.recyclerview.widget.GridLayoutManager
import com.products.presentation.feature.main.MainViewModel
import com.products.presentation.feature.main.state.MainAction
import com.products.presentation.feature.products.adapter.ProductCategoryAdapter
import com.products.presentation.feature.products.adapter.ProductListAdapter
import com.products.presentation.feature.products.state.ProductAction
import com.products.presentation.feature.products.state.ProductErrorState
import com.products.presentation.utils.ProductCategoryKeyProvider
import com.products.presentation.utils.ProductCategoryLookUp
import com.products.presentation.utils.ProductCategoryType
import com.products.presentation.utils.RECYCLER_VIEW_MARGIN_SMALL_DP
import com.products.presentation.utils.product_selection_category_tacker
import com.rob_product_common.base.BaseViewModelFragment
import com.rob_product_common.extensions.convertDpToPx
import com.rob_product_common.extensions.showToast
import com.rob_product_common.utils.autoCleared
import com.rob_product_common.utils.launchWithTimber
import com.rob_product_common.utils.recycleviewselection.selectionTrackerProvider
import com.rob_product_common.utils.viewLifecycleScope
import com.rob_product_common.widget.MarginItemDecoration
import com.rob_products_app.R
import com.rob_products_app.databinding.FragmentListProductsBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map

@AndroidEntryPoint
class ProductListFragment :
    BaseViewModelFragment<FragmentListProductsBinding, ProductListViewModel>() {
    private var adapter: ProductListAdapter by autoCleared()

    override fun isExitOnBack(): Boolean =
        true

    override fun canBack(): Boolean = true
    val mainViewModel: MainViewModel by activityViewModels()
    private var productCategory: ProductCategoryAdapter by autoCleared()
    override fun getLayoutId(): Int = R.layout.fragment_list_products
    private val selectionTrackerProvider by selectionTrackerProvider()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.setUpAdapter()
        setUpRefreshLayout()
        setUpVmObservers()
    }

    private fun FragmentListProductsBinding.setUpAdapter() {
        productCategory =
            ProductCategoryAdapter(onProductCategoryClicked = { categoryName, isProductSelected ->
                viewModel.action(
                    ProductAction.ClickProductCategory(
                        categoryName = categoryName,
                        isUserSelectProductCategory = isProductSelected,
                    ),
                )
            })
        productCategoryRecyleView.adapter = productCategory
        productCategoryRecyleView.addItemDecoration(
            MarginItemDecoration(
                spaceSize = requireContext().convertDpToPx(
                    RECYCLER_VIEW_MARGIN_SMALL_DP,
                ),
                orientation = GridLayoutManager.HORIZONTAL,
            ),
        )
        productCategory.tracker = createSelectionTracker()
        adapter = ProductListAdapter(
            onProductClicked = { product ->
                viewModel.action(ProductAction.ClickProducts(product))
            },
            onProductAddedToCart = { product ->
                mainViewModel.action(MainAction.ClickAddProductToCart(product))
            },
        )
        productsRecyleView.adapter = adapter
        productsRecyleView.addItemDecoration(
            MarginItemDecoration(
                spaceSize = requireContext().convertDpToPx(RECYCLER_VIEW_MARGIN_SMALL_DP),
            ),
        )
    }

    private fun createSelectionTracker() =
        selectionTrackerProvider.createSelectionTracker(
            binding.productCategoryRecyleView,
            ProductCategoryKeyProvider(productCategory),
            detailsLookup = ProductCategoryLookUp(binding.productCategoryRecyleView),
            selectionTrackerKey = product_selection_category_tacker,
            storageStrategy = StorageStrategy.createStringStorage(),
            selectionPredicate = SelectionPredicates.createSelectSingleAnything(),
        )

    private fun setUpVmObservers() {
        viewLifecycleScope.launchWithTimber {
            repeatOnLifecycle(state = Lifecycle.State.CREATED) {
                viewModel.viewState.collectLatest { state ->
                    binding.swipeRefreshLayout.isRefreshing = state.isLoading
                    adapter.submitList(state.listOfProducts)
                }
            }
        }
        viewLifecycleScope.launchWithTimber {
            repeatOnLifecycle(state = Lifecycle.State.CREATED) {
                viewModel.errorState.collectLatest { errorState ->
                    when (errorState) {
                        is ProductErrorState.CommonError -> {
                            requireContext().showToast(message = errorState.error.localizedMessage.orEmpty())
                        }
                    }
                }
            }
        }
        viewLifecycleScope.launchWithTimber {
            repeatOnLifecycle(state = Lifecycle.State.CREATED) {
                viewModel.viewState.map { it.distinctProductCategories }
                    .collectLatest { distinctProductCategories ->
                        val mutableDistinctProductCategory =
                            distinctProductCategories.toMutableList()
                        if (mutableDistinctProductCategory.isNotEmpty()) {
                            mutableDistinctProductCategory.add(
                                0,
                                ProductCategoryType.ALL_X.categoryType,
                            )
                            productCategory.submitList(
                                mutableDistinctProductCategory,
                            )
                        }
                    }
            }
        }
        viewLifecycleScope.launchWithTimber {
            repeatOnLifecycle(state = Lifecycle.State.CREATED) {
                viewModel.viewState.map { it.selectedCategory }
                    .distinctUntilChanged()
                    .collectLatest { selectedCategory ->
                        productCategory.tracker?.select(selectedCategory)
                    }
            }
        }
    }

    private fun setUpRefreshLayout() {
        binding.swipeRefreshLayout.apply {
            isRefreshing = false
            setOnRefreshListener {
                viewModel.action(ProductAction.RefreshListOfProducts)
            }
        }
    }

    override fun setActivityAsViewModelProvider(): Boolean {
        return true
    }
}
