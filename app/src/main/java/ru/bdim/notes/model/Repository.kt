package ru.bdim.notes.model

import android.graphics.Color

object Repository {
    private val notes: List<Note>

    init {
        notes = listOf(
            Note(title = "Москва", text = "Город - герой", color = Color.RED),
            Note("Питер", "Культурная столица", Color.YELLOW),
            Note(color = Color.CYAN, text = "Столица красных фонарей", title = "Амстердам"),
            Note("Вена", "Столица классической музыки", Color.GREEN)
        )
    }

    fun getNotes() = notes
}