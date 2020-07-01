package ru.bdim.notes.viewmodel

import ru.bdim.notes.model.Note
import ru.bdim.notes.model.NoteResult
import ru.bdim.notes.model.NoteViewState
import ru.bdim.notes.model.Repository

class NoteViewModel (val repository: Repository)
    : BaseViewModel<NoteViewState.Data, NoteViewState>(){

    private var note: Note? = null
        get() = viewStateLD.value?.data?.note

    fun saveNote(note: Note){
        viewStateLD.value = NoteViewState(NoteViewState.Data(note = note))
    }

    override fun onCleared() {
        note?.let {
            repository.setNote(it)
        }
        super.onCleared()
    }

    fun loadNote(noteId: String) {
        repository.getNote(noteId).observeForever {
            it ?: return@observeForever
            viewStateLD.value = when (it) {
                is NoteResult.Success<*> -> {
                    NoteViewState(NoteViewState.Data(note = it.data as? Note))
                }
                is NoteResult.Error -> {
                    NoteViewState(error = it.e)
                }
            }
        }
    }
    fun deleteNote() {
        note?.let {
            repository.deleteNote(it.id).observeForever { t ->
                t?.let {
                    viewStateLD.value = when (it) {
                        is NoteResult.Success<*> -> NoteViewState(NoteViewState.Data(isDeleted = true))
                        is NoteResult.Error -> NoteViewState(error = it.e)
                    }
                }
            }
        }
    }
}