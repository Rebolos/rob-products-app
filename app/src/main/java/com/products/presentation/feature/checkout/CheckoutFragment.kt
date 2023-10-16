package com.products.presentation.feature.checkout

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.repeatOnLifecycle
import com.products.presentation.feature.checkout.state.CheckoutAction
import com.products.presentation.feature.checkout.state.CheckoutActionState
import com.products.presentation.feature.main.MainViewModel
import com.products.presentation.feature.main.state.MainAction
import com.rob_product_common.base.BaseViewModelFragment
import com.rob_product_common.extensions.debounceTextChanges
import com.rob_product_common.extensions.ninjaTap
import com.rob_product_common.extensions.showToast
import com.rob_product_common.utils.launchWithTimber
import com.rob_product_common.utils.viewLifecycleScope
import com.rob_products_app.R
import com.rob_products_app.databinding.FragmentCheckoutBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.mapNotNull

@AndroidEntryPoint
class CheckoutFragment : BaseViewModelFragment<FragmentCheckoutBinding, CheckoutViewModel>() {
    val mainViewModel: MainViewModel by activityViewModels()
    override fun getLayoutId(): Int = R.layout.fragment_checkout

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.setUpView()
        setUpVmObservers()
    }

    private fun FragmentCheckoutBinding.setUpView() {
        btnPay.ninjaTap(viewLifecycleScope) {
            clearErrors()
            viewModel.action(CheckoutAction.SubmitUserOrder(customSwitch.isChecked()))
        }

        viewLifecycleScope.launchWithTimber {
            txtYourNameEditText
                .debounceTextChanges()
                .distinctUntilChanged()
                .collectLatest { userEmail ->
                    viewModel.action(CheckoutAction.UserInputEmail(userEmail))
                }
        }
        viewLifecycleScope.launchWithTimber {
            txtInputEmailEditText
                .debounceTextChanges()
                .distinctUntilChanged()
                .collectLatest { nameInput ->
                    viewModel.action(CheckoutAction.UserInputName(nameInput))
                }
        }
    }

    private fun setUpVmObservers() {
        viewLifecycleScope.launchWithTimber {
            repeatOnLifecycle(state = Lifecycle.State.CREATED) {
                viewModel.viewState.mapNotNull { it.price }
                    .distinctUntilChanged()
                    .collectLatest { orderPrice ->
                        binding.btnPay.text = getString(R.string.total, orderPrice)
                    }
            }
        }

        viewLifecycleScope.launchWithTimber {
            repeatOnLifecycle(state = Lifecycle.State.CREATED) {
                viewModel.actionState.distinctUntilChanged().collectLatest { actionState ->
                    handleActionState(actionState)
                }
            }
        }
    }

    private fun handleActionState(actionState: CheckoutActionState) {
        when (actionState) {
            CheckoutActionState.CartListIsRequired -> showToastErrorMessage(getString(R.string.cart_list_field_is_required))
            CheckoutActionState.CheckoutFieldsError -> handleCheckoutFieldsError()
            CheckoutActionState.InvalidEmailError -> handleInvalidEmailError()
            CheckoutActionState.InvalidNameError -> handleInvalidNameError()
            is CheckoutActionState.NavigateToOrderConfirmation -> {
                Log.d("TEST","Navigate order confirmation")
                mainViewModel.action(MainAction.RefreshCartCount)
            }

            CheckoutActionState.TermsAndConditionIsRequired -> {
                showToastErrorMessage(getString(R.string.terms_and_condition_is_required_error_message))
            }
        }
    }

    private fun showToastErrorMessage(message: String) {
        requireContext().showToast(message = message)
    }

    private fun handleCheckoutFieldsError() {
        binding.textInputYourEmailLayout.error = getString(R.string.email_must_not_be_empty)
        binding.textInputYourNameLayout.error = getString(R.string.first_name_error_message)
    }

    private fun handleInvalidEmailError() {
        binding.textInputYourNameLayout.error = ""
        binding.textInputYourEmailLayout.error = getString(R.string.email_must_not_be_empty)
    }

    private fun handleInvalidNameError() {
        binding.textInputYourEmailLayout.error = ""
        binding.textInputYourNameLayout.error = getString(R.string.first_name_error_message)
    }

    private fun clearErrors() {
        binding.textInputYourEmailLayout.error = ""
        binding.textInputYourNameLayout.error = ""
    }
}
