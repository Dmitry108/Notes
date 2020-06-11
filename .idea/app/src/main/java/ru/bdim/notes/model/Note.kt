package ru.bdim.notes.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
data class Note (val id: String,
                 val title: String, val text: String,
                 val color: NoteColor = NoteColor.WHITE,
                 val lastDate: Date = Date()): Parcelable{

    override fun equals(other: Any?): Boolean = when {
        other === this -> true
        javaClass != other?.javaClass || (other as Note).id != id -> false
        else -> true
    }

    enum class NoteColor{
        WHITE, RED, ORANGE, YELLOW, GREEN, BLUE, VIOLET
    }
}