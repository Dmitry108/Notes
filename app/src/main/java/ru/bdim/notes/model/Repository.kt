package ru.bdim.notes.model

import ru.bdim.notes.model.source.DataProvider

class Repository (val dataSource : DataProvider){
    fun getNotes() = dataSource.subscribeToNotes()
    fun setNote(note: Note) = dataSource.setNote(note)
    fun getNote(id: String) = dataSource.getNote(id)
    fun getUser() = dataSource.getUser()
    fun deleteNote(noteId: String) = dataSource.deleteNote(noteId)
}