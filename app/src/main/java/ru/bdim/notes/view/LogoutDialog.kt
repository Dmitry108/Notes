package ru.bdim.notes.view

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatDialogFragment
import ru.bdim.notes.R

class LogoutDialog : AppCompatDialogFragment(){

    var listener: (()->Unit)? = null

    companion object{
        val TAG = "logout dialog"
        fun create(listener: (()->Unit)?) =
            LogoutDialog().apply{ this.listener = listener }
    }
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog =
        context?.let {
            AlertDialog.Builder(it)
                .setTitle(R.string.logout)
                .setMessage(R.string.sure)
                .setPositiveButton(R.string.yes) { dialog, which -> listener?.invoke() }
                .setNegativeButton(R.string.no) {_, _ -> dismiss()}
                .create()
        } ?: super.onCreateDialog(savedInstanceState)
    override fun onDetach() {
        listener = null
        super.onDetach()
    }
}