package ru.bdim.notes.viewmodel

import kotlinx.coroutines.launch
import ru.bdim.notes.model.*
import ru.bdim.notes.model.auth.AuthException

class SplashViewModel(val repository: Repository) : BaseViewModel<Boolean?, SplashViewState>() {
    fun getUser()  = launch {
        repository.getUser()?.let {
            setData(SplashViewState(isAuth = true))
        } ?: setData(SplashViewState(error = AuthException()))
            //setError(AuthException())
    }
}