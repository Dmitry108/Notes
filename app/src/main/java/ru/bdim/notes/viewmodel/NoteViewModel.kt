package ru.bdim.notes.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.bdim.notes.model.NoteViewState
import ru.bdim.notes.model.Repository

class NoteViewModel : ViewModel(){
    private val noteViewStateLiveData : MutableLiveData<NoteViewState> = MutableLiveData()

    init {
        noteViewStateLiveData.value = NoteViewState(Repository.getNotes())
    }

    fun viewState() : LiveData<NoteViewState> = noteViewStateLiveData
}