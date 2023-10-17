package com.products.presentation.utils

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.FrameLayout
import android.widget.ImageView
import com.rob_product_common.R

class CustomSwitch(context: Context, attrs: AttributeSet) : FrameLayout(context, attrs) {
    private val background: ImageView
    private val thumb: ImageView
    private var isChecked: Boolean = false

    companion object {
        const val HORIZONTAL_POSITION = "translationX"
    }

    init {
        View.inflate(context, R.layout.custom_switch, this)
        background = findViewById(R.id.switch_background)
        thumb = findViewById(R.id.switch_thumb)

        // Set initial state and click listener
        setChecked(isChecked)
        setOnClickListener {
            toggle()
        }
    }

    private fun toggle() {
        animateThumb()
        setChecked(isChecked)
    }

    private fun setChecked(checked: Boolean) {
        isChecked = checked
        if (isChecked) {
            background.setImageResource(R.drawable.custom_switch_track_on)
            thumb.setImageResource(R.drawable.custom_switch_thumb_on)
        } else {
            background.setImageResource(R.drawable.custom_switch_track)
            thumb.setImageResource(R.drawable.custom_switch_thumb)
        }
    }

    fun isChecked(): Boolean {
        return isChecked
    }

    private fun animateThumb() {
        val thumbView = thumb
        val property = HORIZONTAL_POSITION
        val startTranslationX = thumbView.translationX
        val endTranslationX: Float

        val thumbWidth = thumbView.width
        val trackWidth = background.width
        endTranslationX = if (isChecked) {
            trackWidth - thumbWidth.toFloat()
        } else {
            0f
        }

        val interpolator = AccelerateDecelerateInterpolator()

        val animator =
            ObjectAnimator.ofFloat(thumbView, property, startTranslationX, endTranslationX)
        animator.duration = 200
        animator.interpolator = interpolator

        animator.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                isChecked = !isChecked
            }
        })

        animator.start()
    }
}
