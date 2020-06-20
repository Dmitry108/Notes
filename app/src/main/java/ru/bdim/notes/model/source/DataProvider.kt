package ru.bdim.notes.model.source

import androidx.lifecycle.LiveData
import ru.bdim.notes.model.Note
import ru.bdim.notes.model.NoteResult

interface DataProvider{
    fun subscribeToNotes(): LiveData<NoteResult>
    fun getNote(id: String): LiveData<NoteResult>
    fun setNote(note: Note): LiveData<NoteResult>
}