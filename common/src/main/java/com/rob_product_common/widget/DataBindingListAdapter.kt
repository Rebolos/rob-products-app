package com.rob_product_common.widget

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.CallSuper
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.AsyncDifferConfig
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.rob_product_common.utils.createDiffUtilItemCallback
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.asExecutor
import java.util.concurrent.Executor

abstract class DataBindingListAdapter<T : Any, VB : ViewDataBinding>(
    backgroundExecutor: Executor = Dispatchers.Default.asExecutor(),
    contentChecker: (T, T) -> Boolean = DefaultContentChecker,
    idProvider: (T) -> Any = DefaultIdProvider,
    changePayload: (T, T) -> Any? = DefaultChangePayload,
) : ListAdapter<T, DataBindingListAdapter<T, VB>.ViewHolder>(
    AsyncDifferConfig.Builder<T>(
        createDiffUtilItemCallback(
            areItemsTheSame = { oldItem, newItem -> idProvider(oldItem) == idProvider(newItem) },
            areContentsTheSame = contentChecker,
            getChangePayload = changePayload,
        ),
    ).setBackgroundThreadExecutor(backgroundExecutor).build(),
) {

    companion object {
        private val DefaultContentChecker: (Any, Any) -> Boolean = { old, new -> old == new }
        private val DefaultIdProvider: (Any) -> Any = { it }
        private val DefaultChangePayload: (Any, Any) -> Any? = { _, _ -> null }
    }

    abstract val bindingItemId: Int

    @get:LayoutRes
    abstract val layoutRes: Int

    inner class ViewHolder(
        val binding: VB,
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: T) {
            bindItem(binding, item)
        }
    }

    fun getBindingFromViewHolder(viewHolder: RecyclerView.ViewHolder): VB =
        DataBindingUtil.bind(viewHolder.itemView)!!

    protected open fun onBindingCreated(binding: VB) = Unit

    @CallSuper
    protected open fun bindItem(binding: VB, item: T) {
        binding.setVariable(bindingItemId, item)
        binding.executePendingBindings()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position)!!)
    }

    final override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                layoutRes,
                parent,
                false,
            ),
        ).apply { onBindingCreated(binding) }
    }
}
