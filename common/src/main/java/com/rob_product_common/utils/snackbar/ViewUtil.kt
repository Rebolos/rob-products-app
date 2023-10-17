package com.rob_product_common.utils.snackbar

import android.annotation.SuppressLint
import android.view.View
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar
import com.rob_products.commonstyles.R

const val SNACKBAR_DURATION = 3000

object ViewUtils {
    fun showGenericSuccessSnackBar(
        parentView: View,
        text: String,
    ) {
        showSuccessSnackBar(
            parentView,
            text,
            parentView
                .context
                .getString(
                    com.rob_products.commonstyles.R.string.hide,
                ),
        ) {
            it.dismiss()
        }
    }

    fun showGenericErrorSnackBar(
        parentView: View,
        text: String,
    ) {
        showErrorSnackBar(
            parentView,
            text,
            parentView
                .context
                .getString(
                    R.string.hide,
                ),
        ) {
            it.dismiss()
        }
    }

    @SuppressLint("WrongConstant")
    fun showSuccessSnackBar(
        parentView: View,
        text: String,
        actionText: String,
        actionClickListener: (Snackbar) -> Unit,
    ): Snackbar {
        return Snackbar.make(parentView, text, SNACKBAR_DURATION)
            .apply {
                setAction(actionText) {
                    actionClickListener(this)
                }
                setTextColor(ContextCompat.getColor(parentView.context, R.color.colorOnAlertSuccess))
                setActionTextColor(
                    ContextCompat.getColor(
                        parentView.context,
                        R.color.colorOnAlertSuccess,
                    ),
                )
                setBackgroundTintList(
                    ContextCompat.getColorStateList(
                        parentView.context,
                        R.color.colorAlertSuccess,
                    ),
                )
                show()
            }
    }

    @SuppressLint("WrongConstant")
    fun showErrorSnackBar(
        parentView: View,
        text: String,
        actionText: String,
        actionClickListener: (Snackbar) -> Unit,
    ): Snackbar {
        return Snackbar.make(parentView, text, SNACKBAR_DURATION)
            .apply {
                setAction(actionText) {
                    actionClickListener(this)
                }
                setTextColor(ContextCompat.getColor(parentView.context, R.color.colorOnAlertError))
                setActionTextColor(
                    ContextCompat.getColor(
                        parentView.context,
                        R.color.colorOnAlertError,
                    ),
                )
                setBackgroundTintList(
                    ContextCompat.getColorStateList(
                        parentView.context,
                        R.color.colorAlertError,
                    ),
                )
                show()
            }
    }
}
