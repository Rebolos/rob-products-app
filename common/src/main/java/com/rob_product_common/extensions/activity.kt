package com.rob_product_common.extensions

import android.app.Activity

fun Activity.convertDpToPx(dp: Float): Float {
    return dp * this.resources.displayMetrics.density
}