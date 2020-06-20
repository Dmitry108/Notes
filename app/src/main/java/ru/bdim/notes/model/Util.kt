package ru.bdim.notes.model

import ru.bdim.notes.R
import java.util.*

fun takeId() = UUID.randomUUID().toString()
fun takeColor(color: Note.NoteColor) = when (color) {
    Note.NoteColor.WHITE -> R.color.white
    Note.NoteColor.RED -> R.color.red
    Note.NoteColor.ORANGE -> R.color.orange
    Note.NoteColor.YELLOW -> R.color.yellow
    Note.NoteColor.GREEN -> R.color.green
    Note.NoteColor.BLUE -> R.color.blue
    Note.NoteColor.VIOLET -> R.color.violet
}