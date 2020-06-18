package ru.bdim.notes.model

open class BaseViewState<T>(val data: T, val e: Throwable?)

class MainViewState(notes: List<Note>? = null, error: Throwable? = null)
    : BaseViewState<List<Note>?>(notes, error)

class NoteViewState(note: Note? = null, error: Throwable? = null)
    : BaseViewState<Note?>(note, error)
