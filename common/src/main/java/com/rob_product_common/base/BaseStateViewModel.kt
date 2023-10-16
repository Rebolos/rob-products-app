package com.rob_product_common.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rob_product_common.utils.launchWithTimber
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow

abstract class BaseStateViewModel<VS : Any, AS : Any, ES : Any, A : Any> : ViewModel() {

    protected val mutableViewState: MutableStateFlow<VS> by lazy { MutableStateFlow(initialState) }
    protected val mutableActionState: MutableSharedFlow<AS> = MutableSharedFlow()
    protected val mutableErrorState: MutableSharedFlow<ES> = MutableSharedFlow()

    val action: (A) -> Unit

    protected val actionFlow: MutableSharedFlow<A> = MutableSharedFlow(replay = 1)

    protected abstract val initialState: VS

    init {
        action = { action ->
            viewModelScope.launchWithTimber {
                actionFlow.emit(action)
            }
        }
    }

    val viewState: StateFlow<VS> by lazy { mutableViewState.asStateFlow() }
    val actionState: SharedFlow<AS> by lazy { mutableActionState.asSharedFlow() }
    val errorState: SharedFlow<ES> by lazy { mutableErrorState.asSharedFlow() }
}
