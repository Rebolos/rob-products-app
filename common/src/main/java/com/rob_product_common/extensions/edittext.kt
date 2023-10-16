package com.rob_product_common.extensions

import android.widget.EditText
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.map
import reactivecircus.flowbinding.android.widget.textChanges
import kotlin.time.Duration.Companion.milliseconds

const val TEXT_WATCHER_DEBOUNCE_TIME = 500L
fun EditText.debounceTextChanges(): Flow<String> {
    return textChanges()
        .skipInitialValue()
        .debounce(TEXT_WATCHER_DEBOUNCE_TIME.milliseconds)
        .map { it.toString() }
}