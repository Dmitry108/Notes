package ru.bdim.notes.model.source

import kotlinx.coroutines.channels.ReceiveChannel
import ru.bdim.notes.model.Note
import ru.bdim.notes.model.NoteResult
import ru.bdim.notes.model.User

interface DataProvider{
    fun subscribeToNotes(): ReceiveChannel<NoteResult>
    suspend fun getNote(id: String): Note
    suspend fun setNote(note: Note): Note
    suspend fun getUser(): User?
    suspend fun deleteNote(noteId: String)
}