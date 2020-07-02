package ru.bdim.notes.model.source

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.ReceiveChannel
import ru.bdim.notes.model.Note
import ru.bdim.notes.model.NoteResult
import ru.bdim.notes.model.User
import ru.bdim.notes.model.auth.AuthException
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

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

    override fun subscribeToNotes(): ReceiveChannel<NoteResult> =
        Channel<NoteResult>(Channel.CONFLATED).apply {
            var listener: ListenerRegistration? = null
            try {
                listener = userNotes.addSnapshotListener(EventListener<QuerySnapshot> { snapshot, e ->
                    val result = e?.let {
                        NoteResult.Error(it)
                    } ?: snapshot?.let {
                        NoteResult.Success(it.documents.map {
                            it.toObject(Note::class.java)
                        })
                    }
                    result?.let { offer(it) }
                })
            } catch (e: Throwable){
                offer(NoteResult.Error(e))
            }
            invokeOnClose {
                listener?.remove()
            }
        }
    override suspend fun getNote(id: String): Note =
        suspendCoroutine { cont ->
            try {
                userNotes.document(id).get()
                    .addOnSuccessListener {
                        cont.resume(it.toObject(Note::class.java)!!)
                    }
                    .addOnFailureListener {
                        cont.resumeWithException(it)
                    }
            } catch (e: Throwable){
                cont.resumeWithException(e)
            }
        }
    override suspend fun setNote(note: Note): Note =
        suspendCoroutine { cont ->
            try {
                userNotes.document(note.id)
                    .set(note)
                    .addOnSuccessListener {
                        cont.resume(note)
                    }
                    .addOnFailureListener {
                        cont.resumeWithException(it)
                    }
            } catch (e: Throwable){
                cont.resumeWithException(e)
            }
        }
    override suspend fun getUser(): User? =
        suspendCoroutine { cont ->
            user?.let {
                User(it.displayName ?: "", it.email ?: "")
            }.let { user -> cont.resume(user) }
        }

    override suspend fun deleteNote(noteId: String): Unit =
        suspendCoroutine { cont ->
            try {
                userNotes.document(noteId).delete()
                    .addOnSuccessListener {
                        cont.resume(Unit)
                    }.addOnFailureListener {
                        cont.resumeWithException(it)
                    }
            } catch (e: Throwable) {
                cont.resumeWithException(e)
            }
        }
}