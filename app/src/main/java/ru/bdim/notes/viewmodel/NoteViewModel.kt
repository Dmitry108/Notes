package ru.bdim.notes.viewmodel

import kotlinx.coroutines.launch
import ru.bdim.notes.model.*

class NoteViewModel (val repository: Repository)
    : BaseViewModel<NoteViewState.Data, NoteViewState>(){

    private val note: Note?
        get() = getViewState().poll()?.data?.note

    fun saveNote(note: Note){
        setData(NoteViewState(data = NoteViewState.Data(note = note)))
    }
    override fun onCleared() {
        launch {
            note?.let {
                repository.setNote(it)
            }
            super.onCleared()
        }
    }
    fun loadNote(noteId: String) = launch {
        try {
            setData(NoteViewState(NoteViewState.Data(note = repository.getNote(noteId))))
        } catch (e: Throwable) {
            //setError(e)
            setData(NoteViewState(error = e))
        }
    }
    fun deleteNote() = launch {
        try {
            note?.let { repository.deleteNote(it.id) }
            setData(NoteViewState(NoteViewState.Data(isDeleted = true)))
        } catch (e: Throwable) {
            //setError(e)
            setData(NoteViewState(error = e))
        }
    }
}