package ru.bdim.notes.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.MenuItem
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.activity_note.*
import ru.bdim.notes.R
import ru.bdim.notes.model.*
import ru.bdim.notes.viewmodel.NoteViewModel
import java.util.*

class NoteActivity : BaseActivity<Note?, NoteViewState>() {

    override val viewModel: NoteViewModel by lazy {
        ViewModelProviders.of(this).get(NoteViewModel::class.java)
    }
    override val layoutId: Int = R.layout.activity_note
    private var note: Note? = null

    private val textListener: TextWatcher = object : TextWatcher {
        override fun afterTextChanged(s: Editable?) { saveNote() }
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
    }
    companion object {
        private const val NOTE = "note"
        fun startActivity(context: Context, id: String? = null) =
            Intent(context, NoteActivity::class.java).run {
                id?.let {
                    putExtra(NOTE, it)
                }
                context.startActivity(this)
            }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSupportActionBar(tlb_note)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val noteId = intent.getStringExtra(NOTE)
        noteId?.let {
            viewModel.loadNote(it)
        } ?: let {
            supportActionBar?.title = getString(R.string.new_note)
        }
        initView()
    }
    override fun renderData(data: Note?) {
        this.note = data
        supportActionBar?.title = note?.let {
            it.lastDate.format()
        } ?: getString(R.string.new_note)
        initView()
    }
    private fun initView() {
        etx_note_title.removeTextChangedListener(textListener)
        etx_note_body.removeTextChangedListener(textListener)

        note?.let {
            etx_note_title.setText(it.title)
            etx_note_body.setText(it.text)
            tlb_note.setBackgroundColor(it.color.takeColor(applicationContext))
        }

        etx_note_title.addTextChangedListener(textListener)
        etx_note_body.addTextChangedListener(textListener)
    }
    fun saveNote() {
        if (etx_note_title.text == null || etx_note_title!!.text!!.length < 3) return
        note = note?.copy(
            title = etx_note_title.text.toString(),
            text = etx_note_body.text.toString(),
            lastDate = Date()
        ) ?: Note(takeId(), etx_note_title.text.toString(), etx_note_body.text.toString())
        note?.let {
            viewModel.saveNote(it)
        }
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean =
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
}