package ru.bdim.notes.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.activity_main.*
import ru.bdim.notes.R
import ru.bdim.notes.viewmodel.MainViewModel

class MainActivity : AppCompatActivity() {

    lateinit var viewModel: MainViewModel
    lateinit var adapter: NotesRecyclerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(tlb_main)

        adapter = NotesRecyclerAdapter{
            NoteActivity.startActivity(this, it)
        }
        rcw_notes.adapter = adapter

        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        viewModel.viewState().observe(this, Observer {
            it?.let { adapter.notes = it.notes }
        })

        fab_note.setOnClickListener{
            NoteActivity.startActivity(this)
        }
    }
}