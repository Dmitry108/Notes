package ru.bdim.notes.viewmodel

import ru.bdim.notes.model.*
import ru.bdim.notes.model.auth.AuthException

class SplashViewModel : BaseViewModel<Boolean?, SplashViewState>() {

    fun getUser() {
        Repository.getUser().observeForever {
            viewStateLD.value = it?.let {
                SplashViewState(isAuth = true)
            } ?: SplashViewState(error = AuthException())
        }
    }
}