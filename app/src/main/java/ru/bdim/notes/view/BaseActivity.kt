package ru.bdim.notes.view

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import ru.bdim.notes.model.BaseViewState
import ru.bdim.notes.viewmodel.BaseViewModel

abstract class BaseActivity<T, S : BaseViewState<T>> : AppCompatActivity() {

    abstract val viewModel: BaseViewModel<T, S>
    abstract val layoutId: Int

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layoutId)

        viewModel.getViewState().observe(this, Observer {
                it ?: return@Observer
                it.data?.let { data -> renderData(data) }
                it.e?.let { error -> renderError(error) }
        })
    }
    abstract fun renderData(data: T)
    protected fun renderError(e: Throwable?) {
        e?.message?.let { showError(it) }
    }
    protected fun showError(error: String) {
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show()
    }
}