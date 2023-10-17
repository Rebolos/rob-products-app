package com.products.presentation.feature.checkout

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.products.presentation.feature.checkout.state.CheckoutAction
import com.products.presentation.feature.checkout.state.CheckoutActionState
import com.products.presentation.feature.main.MainViewModel
import com.products.presentation.feature.main.state.MainAction
import com.rob_product_common.base.BaseViewModelFragment
import com.rob_product_common.extensions.debounceTextChanges
import com.rob_product_common.extensions.ninjaTap
import com.rob_product_common.utils.launchWithTimber
import com.rob_product_common.utils.snackbar.ViewUtils
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
            viewModel.action(CheckoutAction.SubmitUserOrder(customSwitch.isChecked()))
        }

        viewLifecycleScope.launchWithTimber {
            txtYourNameEditText
                .debounceTextChanges()
                .collectLatest { userName ->
                    viewModel.action(CheckoutAction.UserInputName(userName))
                }
        }
        viewLifecycleScope.launchWithTimber {
            txtInputEmailEditText
                .debounceTextChanges()
                .collectLatest { userEmailInput ->
                    viewModel.action(CheckoutAction.UserInputEmail(userEmailInput))
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
                viewModel.actionState.collectLatest { actionState ->
                    handleActionState(actionState)
                }
            }
        }
    }

    private fun handleActionState(actionState: CheckoutActionState) {
        when (actionState) {
            CheckoutActionState.CartListIsRequired -> showError(getString(R.string.cart_list_field_is_required))
            CheckoutActionState.CheckoutFieldsError -> handleCheckoutFieldsError()
            CheckoutActionState.InvalidEmailError -> handleInvalidEmailError()
            CheckoutActionState.InvalidNameError -> handleInvalidNameError()
            is CheckoutActionState.NavigateToOrderConfirmation -> {
                ViewUtils.showGenericSuccessSnackBar(
                    binding.root,
                    getString(R.string.your_order_success_message),
                )
                binding.btnPay.text = getString(R.string.total, 0)
                mainViewModel.action(MainAction.RefreshCartCount)
                findNavController().navigate(
                    CheckoutFragmentDirections.actionCheckoutFragmentToOrderConfirmationFragment(
                        orderId = actionState.orderId.toInt(),
                    ),
                )
            }

            CheckoutActionState.TermsAndConditionIsRequired -> {
                showError(getString(R.string.terms_and_condition_is_required_error_message))
            }

            CheckoutActionState.ClearFieldsError -> {
                binding.textInputYourEmailLayout.error = ""
                binding.textInputYourNameLayout.error = ""
            }
        }
    }

    private fun showError(message: String) {
        ViewUtils.showGenericErrorSnackBar(binding.root, message)
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
}
