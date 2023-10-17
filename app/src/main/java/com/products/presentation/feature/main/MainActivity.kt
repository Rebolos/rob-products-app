package com.products.presentation.feature.main

import android.os.Bundle
import androidx.navigation.findNavController
import com.products.presentation.feature.main.state.MainActionState
import com.products.presentation.feature.main.state.MainErrorState
import com.rob_product_common.base.BaseViewModelActivity
import com.rob_product_common.extensions.setVisible
import com.rob_product_common.extensions.showToast
import com.rob_product_common.utils.collectViewModelState
import com.rob_products_app.MainGraphDirections
import com.rob_products_app.R
import com.rob_products_app.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.distinctUntilChanged

@AndroidEntryPoint
class MainActivity : BaseViewModelActivity<ActivityMainBinding, MainViewModel>() {

    override fun getLayoutId(): Int = R.layout.activity_main

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setUpView()
        setUpVmObservers()
    }

    private fun setUpView() {
        binding.productLayout.icCart.setOnClickListener {
            navController().navigate(MainGraphDirections.actionToCartFragment())
        }
        navController().addOnDestinationChangedListener { _, destination, _ ->
            val title = getTitleForDestination(destinationId = destination.id)
            binding.productLayout.txtDescription.text = title
        }
    }

    private fun getTitleForDestination(destinationId: Int): String {
        return when (destinationId) {
            R.id.cartListFragment -> getString(R.string.cart)
            R.id.productListFragment -> getString(R.string.products)
            R.id.checkoutFragment -> getString(R.string.checkout)
            R.id.orderConfirmationFragment -> getString(R.string.order_confirmation)
            else -> ""
        }
    }

    private fun setUpVmObservers() {
        viewModel.viewState
            .collectViewModelState(this) { state ->
                binding.loading.setVisible(state.isLoading)
                binding.productLayout.txtCartCount.setVisible((state.isShouldShowCart))
                state.cartTotalCount?.let { currentCartTotal ->
                    if (state.isShouldShowCart) {
                        binding.productLayout.txtCartCount.text = currentCartTotal.toString()
                    }
                }
            }

        viewModel.errorState
            .distinctUntilChanged()
            .collectViewModelState(this) { errorState ->
                when (errorState) {
                    is MainErrorState.CommonError -> {
                        showToast(errorState.error.localizedMessage.orEmpty())
                    }
                }
            }

        viewModel.actionState
            .collectViewModelState(this) { actionState ->
                when (actionState) {
                    is MainActionState.ShowSuccessMessage -> {
                        navController().navigate(
                            MainGraphDirections.actionToDialogSuccessFragment(
                                actionState.productName,
                            ),
                        )
                    }
                }
            }
    }

    private fun navController() = findNavController(R.id.nav_host_fragment)
}
