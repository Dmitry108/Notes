package ru.bdim.notes.model

import ru.bdim.notes.model.source.DataProvider

class Repository (val dataSource : DataProvider){
    fun getNotes() = dataSource.subscribeToNotes()
    suspend fun setNote(note: Note) = dataSource.setNote(note)
    suspend fun getNote(id: String) = dataSource.getNote(id)
    suspend fun getUser() = dataSource.getUser()
    suspend fun deleteNote(noteId: String) = dataSource.deleteNote(noteId)
}