package com.products.presentation.utils

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import com.rob_product_common.R

class CustomSwitch(context: Context, attrs: AttributeSet) : FrameLayout(context, attrs) {
    private val background: ImageView
    private val thumb: ImageView
    private var isChecked: Boolean = false

    init {
        View.inflate(context, R.layout.custom_switch, this)
        background = findViewById(R.id.switch_background)
        thumb = findViewById(R.id.switch_thumb)

        // Set initial state and click listener
        setChecked(isChecked)
        setOnClickListener { toggle() }
    }

    fun toggle() {
        setChecked(!isChecked)
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
}
