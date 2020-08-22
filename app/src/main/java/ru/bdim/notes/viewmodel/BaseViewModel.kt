package ru.bdim.notes.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.BroadcastChannel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.ReceiveChannel
import ru.bdim.notes.model.BaseViewState
import kotlin.coroutines.CoroutineContext

open class BaseViewModel<T, S: BaseViewState<T>>: ViewModel(), CoroutineScope {
    override val coroutineContext: CoroutineContext by lazy {
        Dispatchers.Default + Job() + CoroutineName("BaseViewModel")
    }
    private val viewStateChannel = BroadcastChannel<S>(Channel.CONFLATED)
    //private val errorChannel = Channel<Throwable>()

    fun getViewState(): ReceiveChannel<S> = viewStateChannel.openSubscription()
    //fun getError(): ReceiveChannel<Throwable> = errorChannel

    protected fun setData(data: S) = launch {
        viewStateChannel.send(data)
    }
//    protected fun setError(e: Throwable) = launch {
//        errorChannel.send(e)
//    }

    override fun onCleared() {
        viewStateChannel.close()
        //errorChannel.close()
        coroutineContext.cancel()
        super.onCleared()
    }
}