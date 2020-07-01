package ru.bdim.notes.viewmodel

import androidx.lifecycle.Observer
import ru.bdim.notes.model.Note
import ru.bdim.notes.model.NoteResult
import ru.bdim.notes.model.NoteResult.Error
import ru.bdim.notes.model.MainViewState
import ru.bdim.notes.model.Repository

class MainViewModel(repository: Repository) : BaseViewModel<List<Note>?, MainViewState>() {

    private val notesObserver = Observer<NoteResult> {
        it ?: return@Observer
        viewStateLD.value = when(it) {
            is NoteResult.Success<*> ->
                MainViewState(notes = it.data as? List<Note>)
            is Error ->
                MainViewState(error = it.e)
        }
    }
    private val repositoryNotes = repository.getNotes()

    init {
        viewStateLD.value = MainViewState()
        repositoryNotes.observeForever(notesObserver)
    }
    override fun onCleared() {
        repositoryNotes.removeObserver(notesObserver)
        super.onCleared()
    }
}