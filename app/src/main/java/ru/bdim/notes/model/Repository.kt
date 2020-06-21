package ru.bdim.notes.model

import ru.bdim.notes.model.source.DataProvider
import ru.bdim.notes.model.source.FirestoreProvider

object Repository {
    private val dataSource : DataProvider = FirestoreProvider()

    fun getNotes() = dataSource.subscribeToNotes()
    fun setNote(note: Note) = dataSource.setNote(note)
    fun getNote(id: String) = dataSource.getNote(id)
    fun getUser() = dataSource.getUser()
}