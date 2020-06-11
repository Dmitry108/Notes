package ru.bdim.notes.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import ru.bdim.notes.R
import ru.bdim.notes.viewmodel.NoteViewModel

class MainActivity : AppCompatActivity() {

    lateinit var viewModel: NoteViewModel
    lateinit var adapter: NotesRecyclerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProviders.of(this).get(NoteViewModel::class.java)

        adapter = NotesRecyclerAdapter()
        rcw_notes.layoutManager = GridLayoutManager(this, 2)
        rcw_notes.adapter = adapter

        viewModel.viewState().observe(this, Observer {
            it?.let { adapter.notes = it.notes }
        })
    }
}