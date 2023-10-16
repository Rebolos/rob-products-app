package com.rob_product_common.extensions

import android.content.Context
import android.widget.Toast
import androidx.annotation.Dimension

fun Context.showToast(message: String, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, message, duration).show()
}
@Dimension(unit = Dimension.PX)
fun Context.convertDpToPx(dp: Float): Int { // todo put this in the common module
    return (dp * resources.displayMetrics.density + 0.5f).toInt()
}
