package ru.bdim.notes.view

import ru.bdim.notes.model.SplashViewState
import ru.bdim.notes.viewmodel.SplashViewModel
import org.koin.android.viewmodel.ext.android.viewModel

class SplashActivity : BaseActivity<Boolean?, SplashViewState>() {
    override val viewModel: SplashViewModel by viewModel()
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