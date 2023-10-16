package com.rob_product_common.extensions

import android.view.View
import androidx.databinding.BindingAdapter
import com.rob_product_common.utils.throttleFirst
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import reactivecircus.flowbinding.android.view.clicks
import kotlin.time.Duration.Companion.milliseconds

@BindingAdapter("setVisible")
infix fun View.setVisible(isVisible: Boolean?) {
    if (isVisible == true && visibility == View.VISIBLE) {
        return
    }

    if (isVisible == false && visibility == View.GONE) {
        return
    }

    this.visibility = when (isVisible) {
        false -> View.GONE
        else -> View.VISIBLE
    }
}
 inline fun View.ninjaTap(scope: CoroutineScope, crossinline action: (View) -> Unit) {
    scope.launch {
        ninjaTap(action)
    }
 }
const val NINJA_TAP_THROTTLE_TIME = 400L
/**
 * Suspend version of [View.ninjaTap]. Should be called in its own coroutine builder ([launch]) as
 * this suspends indefinitely.
 */
 suspend inline fun View.ninjaTap(crossinline action: (View) -> Unit) {
    this@ninjaTap.clicks()
        .throttleFirst(NINJA_TAP_THROTTLE_TIME.milliseconds)
        .collect {
            action.invoke(this@ninjaTap)
        }
 }
