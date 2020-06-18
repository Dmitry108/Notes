package ru.bdim.notes.view

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.activity_main.*
import ru.bdim.notes.R
import ru.bdim.notes.model.Note
import ru.bdim.notes.model.MainViewState
import ru.bdim.notes.viewmodel.BaseViewModel
import ru.bdim.notes.viewmodel.MainViewModel

class MainActivity : BaseActivity<List<Note>?, MainViewState>() {
    override val viewModel: MainViewModel by lazy { ViewModelProviders.of(this).get(MainViewModel::class.java) }
    override val layoutId: Int = R.layout.activity_main
    private lateinit var adapter: NotesRecyclerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSupportActionBar(tlb_main)

        adapter = NotesRecyclerAdapter{
            NoteActivity.startActivity(this, it.id)
        }
        rcw_notes.adapter = adapter

        fab_note.setOnClickListener{
            NoteActivity.startActivity(this)
        }
    }
    override fun renderData(data: List<Note>?) {
        data?.let {
        adapter.notes = it
    }
}}