package ru.bdim.notes.model.source

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.*
import ru.bdim.notes.model.Note
import ru.bdim.notes.model.NoteResult
import ru.bdim.notes.model.User
import ru.bdim.notes.model.auth.AuthException

class FirestoreProvider (val auth: FirebaseAuth, val store: FirebaseFirestore)
    : DataProvider {
    companion object {
        private const val NOTES = "notes"
        private const val USERS = "users"
    }
    private val user
        get() = auth.currentUser
    private val userNotes
        get() = user?.let {
            store.collection(USERS)
                .document(it.uid).collection(NOTES)
        } ?: throw AuthException()

    override fun subscribeToNotes(): LiveData<NoteResult> =
        MutableLiveData<NoteResult>().apply {
            try {
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
            } catch (e: Throwable){
                value = NoteResult.Error(e)
            }
        }

    override fun getNote(id: String): LiveData<NoteResult> =
        MutableLiveData<NoteResult>().apply {
            try {
                userNotes.document(id).get()
                    .addOnSuccessListener {
                        value = NoteResult.Success(it.toObject(Note::class.java))
                    }
                    .addOnFailureListener {
                        value = NoteResult.Error(it)
                    }
            } catch (e: Throwable){
                value = NoteResult.Error(e)
            }
        }
    override fun setNote(note: Note): LiveData<NoteResult> =
        MutableLiveData<NoteResult>().apply {
            try {
                userNotes.document(note.id)
                    .set(note)
                    .addOnSuccessListener {
                        value = NoteResult.Success(note)
                    }
                    .addOnFailureListener {
                        value = NoteResult.Error(it)
                    }
            } catch (e: Throwable){
                value = NoteResult.Error(e)
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
            try {
                userNotes.document(noteId).delete()
                    .addOnSuccessListener {
                        value = NoteResult.Success(null)
                    }.addOnFailureListener {
                        value = NoteResult.Error(it)
                    }
            } catch (e: Throwable){
                value = NoteResult.Error(e)
            }
        }
}