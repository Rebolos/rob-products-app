package com.products.presentation.feature.orderconfirmation

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.rob_product_common.base.BaseFragment
import com.rob_product_common.extensions.ninjaTap
import com.rob_product_common.utils.viewLifecycleScope
import com.rob_products_app.R
import com.rob_products_app.databinding.FragmentOrderConfirmationBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OrderConfirmationFragment : BaseFragment<FragmentOrderConfirmationBinding>() {
    private val args: OrderConfirmationFragmentArgs by navArgs()
    override fun getLayoutId(): Int = R.layout.fragment_order_confirmation

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.setUpButtons()
        binding.txtOrderId.text = getString(R.string.order_id, args.orderId)
    }

    private fun FragmentOrderConfirmationBinding.setUpButtons() {
        btnReturnToProducts.ninjaTap(viewLifecycleScope) {
            findNavController().navigate(OrderConfirmationFragmentDirections.actionOrderConfirmationFragmentToProductListFragment())
        }
    }
}
