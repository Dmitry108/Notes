package ru.bdim.notes.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.bdim.notes.model.BaseViewState

open class BaseViewModel<T, S : BaseViewState<T>> : ViewModel(){
    open val viewStateLD = MutableLiveData<S>()
    open fun getViewState(): LiveData<S> = viewStateLD
}