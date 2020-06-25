package ru.bdim.notes.model.source

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.*
import ru.bdim.notes.model.Note
import ru.bdim.notes.model.NoteResult
import ru.bdim.notes.model.User
import ru.bdim.notes.model.auth.AuthException

class FirestoreProvider : DataProvider {
    companion object {
        private const val NOTES = "notes"
        private const val USERS = "users"
    }
    private val user
        get() = FirebaseAuth.getInstance().currentUser
    private val userNotes
        get() = user?.let {
            FirebaseFirestore.getInstance().collection(USERS)
                .document(it.uid).collection(NOTES)
        } ?: throw AuthException()

    override fun subscribeToNotes(): LiveData<NoteResult> =
        MutableLiveData<NoteResult>().apply {
            userNotes.addSnapshotListener(EventListener<QuerySnapshot> { snapshot, e ->
                value = e?.let {
                    NoteResult.Error(e)
                } ?: snapshot?.let {
                    val notes = it.documents.map {
                        it.toObject(Note::class.java)
                    }
                    NoteResult.Success(notes)
                }
            })
        }
    override fun getNote(id: String): LiveData<NoteResult> =
        MutableLiveData<NoteResult>().apply {
            userNotes.document(id).get()
                .addOnSuccessListener {
                    value = NoteResult.Success(it.toObject(Note::class.java))
                }
                .addOnFailureListener {
                    value = NoteResult.Error(it)
                }
        }
    override fun setNote(note: Note): LiveData<NoteResult> =
        MutableLiveData<NoteResult>().apply {
            userNotes.document(note.id)
                .set(note)
                .addOnSuccessListener {
                    value = NoteResult.Success(note)
                }
                .addOnFailureListener {
                    value = NoteResult.Error(it)
                }
        }
    override fun getUser(): LiveData<User?> =
        MutableLiveData<User>().apply {
            value = user?.let {
                User(it.displayName ?: "", it.email ?: "")
            }
        }
    override fun deleteNote(noteId: String): LiveData<NoteResult> =
        MutableLiveData<NoteResult>().apply {
            userNotes.document(noteId).delete()
                .addOnSuccessListener {
                    value = NoteResult.Success(null)
                }.addOnFailureListener {
                    value = NoteResult.Error(it)
                }
        }

}