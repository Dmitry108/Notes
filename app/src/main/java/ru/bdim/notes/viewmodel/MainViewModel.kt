package ru.bdim.notes.viewmodel

import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.launch
import ru.bdim.notes.model.Note
import ru.bdim.notes.model.NoteResult
import ru.bdim.notes.model.MainViewState
import ru.bdim.notes.model.Repository

class MainViewModel(repository: Repository) : BaseViewModel<List<Note>?, MainViewState>() {

    private val notes = repository.getNotes()

    init {
        launch {
            notes.consumeEach {
                when (it){
                    is NoteResult.Success<*> -> setData(MainViewState(notes = it.data as? List<Note>))
                    //is NoteResult.Error -> setError(it.e)
                    is NoteResult.Error -> setData(MainViewState(error = it.e))
                }
            }
        }
    }
    override fun onCleared() {
        notes.cancel()
        super.onCleared()
    }
}