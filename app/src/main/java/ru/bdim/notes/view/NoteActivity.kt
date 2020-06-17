package ru.bdim.notes.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.activity_note.*
import ru.bdim.notes.R
import ru.bdim.notes.model.Note
import ru.bdim.notes.model.takeColor
import ru.bdim.notes.model.takeId
import ru.bdim.notes.viewmodel.NoteViewModel
import java.text.SimpleDateFormat
import java.util.*

class NoteActivity : AppCompatActivity() {
    lateinit var noteViewModel: NoteViewModel
    private var note: Note? = null
    private val textListener: TextWatcher = object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
            saveNote()
        }
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
    }

    companion object {
        private const val NOTE = "note"
        private const val DATE_FORMAT = "dd.MM.yy hh:mm"
        fun startActivity(context: Context, note: Note? = null) =
            Intent(context, NoteActivity::class.java).run {
                note?.let {
                    putExtra(NOTE, it)
                }
                context.startActivity(this)
            }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_note)
        setSupportActionBar(tlb_note)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = note?.let {
            SimpleDateFormat(DATE_FORMAT, Locale.getDefault()).format(it.lastDate)
        } ?: getString(R.string.new_note)

        noteViewModel = ViewModelProviders.of(this).get(NoteViewModel::class.java)
        initView()
    }

    fun initView() {
        etx_note_title.removeTextChangedListener(textListener)
        etx_note_body.addTextChangedListener(textListener)

        note = intent.getParcelableExtra(NOTE)

        note?.let {
            etx_note_title.setText(it.title)
            etx_note_body.setText(it.text)
            tlb_note.setBackgroundColor(
                ContextCompat.getColor(applicationContext, takeColor(it.color)))
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
            noteViewModel.saveNote(it)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean =
        when (item.itemId){
            android.R.id.home -> {
                onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
}