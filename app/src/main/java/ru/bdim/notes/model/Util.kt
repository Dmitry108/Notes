package ru.bdim.notes.model

import android.content.Context
import androidx.core.content.ContextCompat
import ru.bdim.notes.R
import java.text.SimpleDateFormat
import java.util.*

const val DATE_FORMAT = "dd.MM.yy hh:mm"

fun takeId() = UUID.randomUUID().toString()

fun Note.NoteColor.takeColor(context: Context) = ContextCompat.getColor(
    context,
    when (this) {
        Note.NoteColor.WHITE -> R.color.white
        Note.NoteColor.RED -> R.color.red
        Note.NoteColor.ORANGE -> R.color.orange
        Note.NoteColor.YELLOW -> R.color.yellow
        Note.NoteColor.GREEN -> R.color.green
        Note.NoteColor.BLUE -> R.color.blue
        Note.NoteColor.VIOLET -> R.color.violet
    })

fun Date.format() =
    SimpleDateFormat(DATE_FORMAT, Locale.getDefault()).format(this)