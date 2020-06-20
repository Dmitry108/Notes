package ru.bdim.notes.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_recycler.view.*
import ru.bdim.notes.R
import ru.bdim.notes.model.Note
import ru.bdim.notes.model.takeColor

class NotesRecyclerAdapter (val onItemClick : ((Note)->Unit)? = null): RecyclerView.Adapter<NotesRecyclerAdapter.NoteViewHolder>() {
    var notes : List<Note> = listOf()
        set(note){
            field = note
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)
            = NoteViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_recycler, parent,false))

    override fun getItemCount(): Int = notes.size

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) = holder.bind(notes[position])

    inner class NoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(note: Note) = with(itemView){
            tvw_title.text = note.title
            tvw_body.text = note.text
            setBackgroundColor(ContextCompat.getColor(context, takeColor(note.color)))
            itemView.setOnClickListener{
                onItemClick?.invoke(note)
            }
        }
    }
}