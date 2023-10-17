package com.products.presentation.feature.cart

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.products.presentation.feature.cart.adapter.CartListAdapter
import com.products.presentation.feature.cart.state.CartAction
import com.products.presentation.feature.cart.state.CartActionState
import com.products.presentation.feature.cart.state.CartErrorState
import com.products.presentation.feature.main.MainViewModel
import com.products.presentation.feature.main.state.MainAction
import com.products.presentation.utils.RECYCLER_VIEW_MARGIN_SMALL_DP
import com.rob_product_common.base.BaseViewModelFragment
import com.rob_product_common.extensions.convertDpToPx
import com.rob_product_common.extensions.ninjaTap
import com.rob_product_common.extensions.setVisible
import com.rob_product_common.extensions.showToast
import com.rob_product_common.utils.autoCleared
import com.rob_product_common.utils.launchWithTimber
import com.rob_product_common.utils.snackbar.ViewUtils
import com.rob_product_common.utils.viewLifecycleScope
import com.rob_product_common.widget.MarginItemDecoration
import com.rob_products_app.R
import com.rob_products_app.databinding.FragmentListCartBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class CartListFragment : BaseViewModelFragment<FragmentListCartBinding, CartListViewModel>() {
    private var cartAdapter: CartListAdapter by autoCleared()
    val mainViewModel: MainViewModel by activityViewModels()
    override fun getLayoutId(): Int = R.layout.fragment_list_cart
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.setUpAdapter()
        binding.setUpViews()
        setUpVmObservers()
    }

    private fun setUpVmObservers() {
        viewLifecycleScope.launchWithTimber {
            repeatOnLifecycle(state = Lifecycle.State.CREATED) {
                viewModel.viewState.collectLatest { state ->
                    state.cartTotal?.let { cartTotal ->
                        binding.txtCartTotal.text = getString(R.string.cart_total, cartTotal)
                    }
                    binding.includeEmptyState.emptyCart.setVisible(state.isShouldCartEmptyState)
                    cartAdapter.submitList(state.cartList)
                    binding.swipeRefreshLayout.isRefreshing = state.isLoading
                }
            }
        }

        viewLifecycleScope.launchWithTimber {
            repeatOnLifecycle(state = Lifecycle.State.CREATED) {
                viewModel.actionState.collectLatest { action ->
                    when (action) {
                        is CartActionState.NavigateToCheckout -> {
                            findNavController().navigate(
                                CartListFragmentDirections.actionCartListFragmentToCheckoutFragment(
                                    cartItem = action.cartList.toTypedArray(),
                                    orderTotal = action.totalPrice,
                                ),
                            )
                        }

                        CartActionState.ShowSuccessDeleteMessage -> {
                            mainViewModel.action(MainAction.RefreshCartCount)
                            ViewUtils.showGenericSuccessSnackBar(
                                binding.root,
                                getString(R.string.cart_item_successfully_deleted_message),
                            )
                        }
                    }
                }
            }
        }
        viewLifecycleScope.launchWithTimber {
            viewModel.errorState.collectLatest { errorState ->
                when (errorState) {
                    is CartErrorState.CommonError -> {
                        requireContext().showToast(errorState.throwable.localizedMessage.orEmpty())
                    }
                }
            }
        }
    }

    private fun FragmentListCartBinding.setUpAdapter() {
        cartAdapter = CartListAdapter(onCartClicked = { cart ->
            viewModel.action(CartAction.ClickCart(cart))
        }, onDeleteCart = { cart ->
            viewModel.action(CartAction.DeleteCart(cart))
        })
        cartRecycleView.adapter = cartAdapter
        cartRecycleView.addItemDecoration(
            MarginItemDecoration(
                spaceSize = requireContext().convertDpToPx(
                    RECYCLER_VIEW_MARGIN_SMALL_DP,
                ),
            ),
        )
    }

    private fun FragmentListCartBinding.setUpViews() {
        swipeRefreshLayout.apply {
            isRefreshing = false
            setOnRefreshListener {
                viewModel.action(CartAction.RefreshCartItems)
            }
        }
        includeEmptyState.txtGoBack.setOnClickListener {
            findNavController().navigateUp()
        }
        btnBuyNow.ninjaTap(viewLifecycleScope) {
            viewModel.action(CartAction.ClickBuyNow)
        }
    }
}
