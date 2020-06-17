package ru.bdim.notes.viewmodel

import androidx.lifecycle.ViewModel
import ru.bdim.notes.model.Note
import ru.bdim.notes.model.Repository

class NoteViewModel : ViewModel(){
    private var note: Note? = null

    fun saveNote(note: Note){
        this.note = note
    }

    override fun onCleared() {
        note?.let {
            Repository.setNote(it)
        }
        super.onCleared()
    }
}