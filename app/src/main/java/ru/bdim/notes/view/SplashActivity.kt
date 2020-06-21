package ru.bdim.notes.view

import androidx.lifecycle.ViewModelProviders
import ru.bdim.notes.model.SplashViewState
import ru.bdim.notes.viewmodel.SplashViewModel

class SplashActivity : BaseActivity<Boolean?, SplashViewState>() {
    override val viewModel: SplashViewModel by lazy {
        ViewModelProviders.of(this).get(SplashViewModel::class.java) }
    override val layoutId: Int? = null

    override fun onResume() {
        super.onResume()
        viewModel.getUser()
    }
    override fun renderData(data: Boolean?) {
        data?.takeIf { it }?.let {
            startMainActivity()
        }
    }
    fun startMainActivity(){
        MainActivity.start(this)
        finish()
    }
}