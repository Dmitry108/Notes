package ru.bdim.notes.model

open class BaseViewState<T>(val data: T, val e: Throwable?)

class MainViewState(notes: List<Note>? = null, error: Throwable? = null)
    : BaseViewState<List<Note>?>(notes, error)

class NoteViewState(data: Data = Data(),
                    error: Throwable? = null)
    : BaseViewState<NoteViewState.Data>(data, error) {
    data class Data(val isDeleted: Boolean = false, val note: Note? = null)
}
class SplashViewState(isAuth: Boolean? = null, error: Throwable? = null)
    : BaseViewState<Boolean?>(isAuth, error)

//data class NoteData(val isDeleted: Boolean = false, val  note: Note? = null)