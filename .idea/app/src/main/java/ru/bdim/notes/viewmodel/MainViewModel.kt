package ru.bdim.notes.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.bdim.notes.model.NotesViewState
import ru.bdim.notes.model.Repository

class MainViewModel : ViewModel(){
    private val notesViewStateLiveData : MutableLiveData<NotesViewState> = MutableLiveData()

    init {
        Repository.getNotes().observeForever {
            it?.let {
                notesViewStateLiveData.value = notesViewStateLiveData.value?.copy(notes = it)
                    ?: NotesViewState(it)
            }
        }
    }

    fun viewState() : LiveData<NotesViewState> = notesViewStateLiveData
}