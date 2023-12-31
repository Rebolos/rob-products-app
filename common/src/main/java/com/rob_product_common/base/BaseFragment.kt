package com.rob_product_common.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.annotation.CallSuper
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.rob_product_common.R

abstract class BaseFragment<B : ViewDataBinding> : Fragment() {

    private var _binding: B? = null
    val binding: B get() = _binding ?: throw NullPointerException("Binding not yet set nor created")

    @LayoutRes
    abstract fun getLayoutId(): Int

    open fun attachToParent(): Boolean = false

    /**
     * Set to `true` when we want to exit app when user taps back or navigates up.
     * Usually used on auth screens before reaching home screen. Default is `false`.
     */
    open fun isExitOnBack(): Boolean = false

    @CallSuper
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = DataBindingUtil.inflate(
            inflater,
            getLayoutId(),
            container,
            attachToParent(),
        )
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (isExitOnBack()) {
            requireActivity()
                .onBackPressedDispatcher
                .addCallback(viewLifecycleOwner) {
                    // Exit app.
                    requireActivity().finishAffinity()
                }
        }
    }

    @CallSuper
    override fun onDestroyView() {
        super.onDestroyView()
        binding.unbind()
        _binding = null
    }

    @CallSuper
    override fun onStop() {
        super.onStop()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (canBack()) {
            if (item.itemId == android.R.id.home) {
                requireActivity().onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    /**
     * @return true if should use back button on toolbar
     */
    protected open fun canBack(): Boolean {
        return false
    }
}
