package com.rob_product_common.widget

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MarginItemDecoration(
    private val spaceSize: Int,
    private val spanCount: Int = 1,
    private val orientation: Int = GridLayoutManager.VERTICAL,
) : RecyclerView.ItemDecoration() {

    constructor() : this(spaceSize = 0, spanCount = 1, orientation = GridLayoutManager.VERTICAL)

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State,
    ) {
        with(outRect) {
            val position = parent.getChildAdapterPosition(view)
            if (orientation == GridLayoutManager.VERTICAL) {
                if (position < spanCount) {
                    top = spaceSize
                }
                if (position % spanCount == 0) {
                    left = spaceSize
                }
            } else {
                if (position < spanCount) {
                    left = spaceSize
                }
                if (position % spanCount == 0) {
                    top = spaceSize
                }
            }
            right = spaceSize
            bottom = spaceSize
        }
    }
}
