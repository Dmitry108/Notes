package ru.bdim.notes.view

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.firebase.ui.auth.AuthUI
import ru.bdim.notes.R
import ru.bdim.notes.model.BaseViewState
import ru.bdim.notes.model.auth.AuthException
import ru.bdim.notes.viewmodel.BaseViewModel

abstract class BaseActivity<T, S : BaseViewState<T>> : AppCompatActivity() {

    companion object{
        const val AUTH_REQUEST_CODE = 1
    }
    abstract val viewModel: BaseViewModel<T, S>
    abstract val layoutId: Int?

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        layoutId?.let {
            setContentView(it)
        }
        viewModel.getViewState().observe(this, Observer {
                it ?: return@Observer
                it.data?.let { data -> renderData(data) }
                it.e?.let { error -> renderError(error) }
        })
    }
    abstract fun renderData(data: T)
    protected fun renderError(e: Throwable?) {
        when (e){
            is AuthException -> startAuth()
            else -> e?.message?.let { showError(it) }
        }
    }
    private fun startAuth() {
        val provider = listOf(
            AuthUI.IdpConfig.GoogleBuilder().build()
        )
        val intent = AuthUI.getInstance()
            .createSignInIntentBuilder()
            .setLogo(R.drawable.book)
            .setTheme(R.style.SplashTheme)
            .setAvailableProviders(provider)
            .build()
        startActivityForResult(intent, AUTH_REQUEST_CODE)
    }
    protected fun showError(error: String) {
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show()
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == AUTH_REQUEST_CODE && resultCode != Activity.RESULT_OK){
            finish()
        }
        super.onActivityResult(requestCode, resultCode, data)
    }
}