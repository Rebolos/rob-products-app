package com.rob_product_common.utils

import androidx.recyclerview.selection.ItemDetailsLookup
import androidx.recyclerview.selection.ItemKeyProvider
import androidx.recyclerview.selection.SelectionTracker
import androidx.recyclerview.selection.StorageStrategy
import androidx.recyclerview.widget.RecyclerView

interface SelectionTrackerFactory {

    fun <T : Any, S : StorageStrategy<T>, P : SelectionTracker.SelectionPredicate<T>> createSelectionTracker(
        recyclerView: RecyclerView,
        keyProvider: ItemKeyProvider<T>,
        detailsLookup: ItemDetailsLookup<T>,
        selectionTrackerKey: String,
        storageStrategy: S,
        selectionPredicate: P
    ): SelectionTracker<T> {
        return SelectionTracker.Builder(
            selectionTrackerKey,
            recyclerView,
            keyProvider,
            detailsLookup,
            storageStrategy
        )
            .withSelectionPredicate(selectionPredicate)
            .build()
    }
}