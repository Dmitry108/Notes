package ru.bdim.notes.model

import ru.bdim.notes.model.source.DataProvider
import ru.bdim.notes.model.source.FirestoreProvider

object Repository {
    private val dataSource : DataProvider = FirestoreProvider()

    fun getNotes() = dataSource.subscribeToNotes()
    fun setNote(note: Note) = dataSource.setNote(note)
    fun getNote(id: String) = dataSource.getNote(id)

//        checkNote(note)
//        notesLiveData.value = notes
//    }
//    private fun checkNote(note: Note) {
//        for (i in notes.indices) {
//            if (notes[i] == note) {
//                notes[i] = note
//                return
//            }
//        }
//        notes.add(note)
//    }

//    private val notesLiveData: MutableLiveData<List<Note>> = MutableLiveData<List<Note>>()
//    private val notes: MutableList<Note> = mutableListOf(
//        Note(takeId(), title = "Москва", text = "Город - герой", color = NoteColor.RED),
//        Note(takeId(), "Питер", "Культурная столица", NoteColor.ORANGE),
//        Note(takeId(), color = NoteColor.YELLOW, text = "Столица красных фонарей", title = "Амстердам"),
//        Note(takeId(), "Вена", "Столица классической музыки", NoteColor.GREEN),
//        Note(takeId(), "Прага", "Столица вкусного пива", NoteColor.BLUE),
//        Note(takeId(), "Трансильвания", "Столица вампиров", NoteColor.VIOLET)
//    )
//
//    init {
//        notesLiveData.value = notes
//    }
//
//    fun getNotes(): LiveData<List<Note>> = notesLiveData
//    fun setNote(note: Note) {
//        checkNote(note)
//        notesLiveData.value = notes
//    }
//    private fun checkNote(note: Note) {
//        for (i in notes.indices) {
//            if (notes[i] == note) {
//                notes[i] = note
//                return
//            }
//        }
//        notes.add(note)
//    }
}