package ru.bdim.notes.viewmodel

import ru.bdim.notes.model.Note
import ru.bdim.notes.model.NoteResult
import ru.bdim.notes.model.NoteViewState
import ru.bdim.notes.model.Repository

class NoteViewModel : BaseViewModel<Note?, NoteViewState>(){
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

    fun loadNote(noteId: String) {
        Repository.getNote(noteId).observeForever {
            it ?: return@observeForever
            when (it) {
                is NoteResult.Success<*> -> {
                    viewStateLD.value = NoteViewState(note = it.data as? Note)
                }
                is NoteResult.Error -> {
                    viewStateLD.value = NoteViewState(error = it.e)
                }
            }
        }
    }
}