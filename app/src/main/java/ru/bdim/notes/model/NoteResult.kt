package ru.bdim.notes.model

sealed class NoteResult {
    data class Success<out T> (val data: T) : NoteResult()
    data class Error(val e: Throwable) : NoteResult()
}