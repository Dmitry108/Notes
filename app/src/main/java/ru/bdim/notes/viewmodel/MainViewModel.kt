package ru.bdim.notes.viewmodel

import androidx.lifecycle.Observer
import ru.bdim.notes.model.Note
import ru.bdim.notes.model.NoteResult
import ru.bdim.notes.model.NoteResult.Error
import ru.bdim.notes.model.MainViewState
import ru.bdim.notes.model.Repository

class MainViewModel : BaseViewModel<List<Note>?, MainViewState>() {

    private val notesObserver = Observer<NoteResult> {
        it ?: return@Observer
        when(it) {
            is NoteResult.Success<*> ->
                viewStateLD.value = MainViewState(notes = it.data as? List<Note>)
            is Error ->
                viewStateLD.value = MainViewState(error = it.e)
        }
    }
    private val repositoryNotes = Repository.getNotes()

    init {
        viewStateLD.value = MainViewState()
        repositoryNotes.observeForever(notesObserver)
    }
    override fun onCleared() {
        repositoryNotes.removeObserver(notesObserver)
        super.onCleared()
    }
}