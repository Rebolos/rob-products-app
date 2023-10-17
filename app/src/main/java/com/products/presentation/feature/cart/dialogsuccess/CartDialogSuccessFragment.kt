package com.products.presentation.feature.cart.dialogsuccess

import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.rob_product_common.base.BaseBottomSheetFragment
import com.rob_product_common.extensions.ninjaTap
import com.rob_product_common.utils.viewLifecycleScope
import com.rob_products_app.R
import com.rob_products_app.databinding.DialogCartSuccessBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CartDialogSuccessFragment : BaseBottomSheetFragment<DialogCartSuccessBinding>() {
    private val args: CartDialogSuccessFragmentArgs by navArgs()
    override fun getLayoutId(): Int = R.layout.dialog_cart_success

    override fun isFullScreen() = false
    override fun shouldExpandOnOpen() = false
    override fun shouldSkipCollapsedState() = true
    override fun isDismissible() = true
    override fun isDraggable() = true
    override fun isTransparent() = false

    override fun setupViews(binding: DialogCartSuccessBinding) {
        binding.apply {
            binding.txtProductName.text = args.cartName.orEmpty()
            binding.btnClose.ninjaTap(viewLifecycleScope) {
                findNavController().navigateUp()
            }
        }
    }
}
