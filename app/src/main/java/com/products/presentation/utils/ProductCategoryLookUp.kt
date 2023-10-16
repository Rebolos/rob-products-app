package com.products.presentation.utils

import android.view.MotionEvent
import androidx.recyclerview.selection.ItemDetailsLookup
import androidx.recyclerview.widget.RecyclerView
import com.products.presentation.feature.products.adapter.ProductCategoryAdapter

class ProductCategoryLookUp(private val recyclerView: RecyclerView) :
    ItemDetailsLookup<String>() {

    override fun getItemDetails(event: MotionEvent): ItemDetails<String>? {
        val view = recyclerView.findChildViewUnder(event.x, event.y)
        if (view != null) {
            return (
                recyclerView.getChildViewHolder(view) as
                    ProductCategoryAdapter.ProductCategoryHolder
                )
                .getItemDetails()
        }
        return null
    }
}
