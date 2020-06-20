package ru.bdim.notes.model.source

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.tasks.OnFailureListener
import com.google.firebase.firestore.*
import ru.bdim.notes.model.Note
import ru.bdim.notes.model.NoteResult


class FirestoreProvider : DataProvider {

    companion object{
        private const val NOTES = "notes"
    }
    private val notes: CollectionReference = FirebaseFirestore.getInstance()
        .collection(NOTES)

    override fun subscribeToNotes(): LiveData<NoteResult> {
        val liveData = MutableLiveData<NoteResult>()
        notes.addSnapshotListener(EventListener<QuerySnapshot>{
            snapshot, e ->
                e?.let {
                    liveData.value = NoteResult.Error(e)
                } ?: snapshot?.let {
                    val notes = it.documents.map {
                        it.toObject(Note::class.java)
                    }
                    liveData.value = NoteResult.Success(notes)
                }
            })
        return liveData
    }
    override fun getNote(id: String): LiveData<NoteResult> {
        val liveData = MutableLiveData<NoteResult>()
        notes.document(id).get()
            .addOnSuccessListener {
                    Log.d("...", "success")
                    liveData.value = NoteResult.Success(it.toObject(Note::class.java))

            }
            .addOnFailureListener {
                OnFailureListener {
                    Log.d("...", "fall")
                    liveData.value = NoteResult.Error(it)
                }
            }
        return liveData
    }

    override fun setNote(note: Note): LiveData<NoteResult> {
        val liveData = MutableLiveData<NoteResult>()

        notes.document(note.id)
            .set(note)
            .addOnSuccessListener {
                liveData.value = NoteResult.Success(note)
            }
            .addOnFailureListener {
                    liveData.value = NoteResult.Error(it)
            }
        return liveData
    }
}