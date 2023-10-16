package com.rob_product_common.utils

import androidx.recyclerview.widget.DiffUtil

fun <T : Any> createDiffUtilItemCallback(
    areItemsTheSame: (T, T) -> Boolean = { old, new -> old == new },
    areContentsTheSame: (T, T) -> Boolean = { old, new -> old == new },
    getChangePayload: (T, T) -> Any? = { _, _ -> null }
): DiffUtil.ItemCallback<T> =
    DefaultDiffUtilItemCallback(areItemsTheSame, areContentsTheSame, getChangePayload)

private class DefaultDiffUtilItemCallback<T : Any>(
    private val areItemsTheSame: (T, T) -> Boolean,
    private val areContentsTheSame: (T, T) -> Boolean,
    private val getChangePayload: (T, T) -> Any?
) : DiffUtil.ItemCallback<T>() {
    override fun areItemsTheSame(oldItem: T, newItem: T): Boolean =
        areItemsTheSame.invoke(oldItem, newItem)

    override fun areContentsTheSame(oldItem: T, newItem: T): Boolean =
        areContentsTheSame.invoke(oldItem, newItem)

    override fun getChangePayload(oldItem: T, newItem: T): Any? =
        getChangePayload.invoke(oldItem, newItem)
}