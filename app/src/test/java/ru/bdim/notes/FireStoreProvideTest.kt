package ru.bdim.notes

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.*
import io.mockk.*
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import ru.bdim.notes.model.Note
import ru.bdim.notes.model.NoteResult
import ru.bdim.notes.model.NoteResult.Success
import ru.bdim.notes.model.auth.AuthException
import ru.bdim.notes.model.source.FirestoreProvider

class FireStoreProvideTest {
//
//    @get:Rule
//    val rule = InstantTaskExecutorRule()
//
//    private val mockAuth: FirebaseAuth = mockk()
//    private val mockStore: FirebaseFirestore = mockk()
//    private val provider = FirestoreProvider(mockAuth, mockStore)
//    private val mockUserNotes: CollectionReference = mockk()
//    private val mockUser: FirebaseUser = mockk()
//
//    private val mockDoc1: DocumentSnapshot = mockk()
//    private val mockDoc2: DocumentSnapshot = mockk()
//    private val testNotes = listOf<Note>(
//        Note("a"), Note("b")
//    )
//
//    @Before
//    fun onBegin(){
//        clearAllMocks()
//        every { mockAuth.currentUser } returns mockUser
//        every { mockUser.uid } returns "uid"
//        every { mockStore.collection(any())
//            .document(any()).collection(any()) } returns mockUserNotes
//        every { mockDoc1.toObject(Note::class.java) } returns testNotes[0]
//        every { mockDoc2.toObject(Note::class.java) } returns testNotes[1]
//    }
//
//    @Test
//    fun `testing noAuthException`(){
//        every { mockAuth.currentUser } returns null
//        var res: Any? = null
//        provider.subscribeToNotes().observeForever{
//            res = (it as NoteResult.Error).e
//        }
//        assert(res is AuthException)
//    }
//    @Test
//    fun `testing onSubscribe getting notes`(){
////      функция
////        userNotes.addSnapshotListener(EventListener<QuerySnapshot> { snapshot, e ->
////            value = e?.let {
////                NoteResult.Error(e)
////            } ?: snapshot?.let {
////                val notes = it.documents.map {
////                    it.toObject(Note::class.java)
////                }
////                NoteResult.Success(notes)
////            }
//        var result: List<Note>? = null
//        val mockSnapshot = mockk<QuerySnapshot>()
//        val slot = slot<EventListener<QuerySnapshot>>()
//        every { mockUserNotes.addSnapshotListener(capture(slot)) } returns mockk()
//        every { mockSnapshot.documents } returns listOf(mockDoc1, mockDoc2)
//        provider.subscribeToNotes().observeForever{
//            result = (it as? Success<List<Note>>)?.data
//        }
//        slot.captured.onEvent(mockSnapshot, null)
//        assertEquals(result, testNotes)
//    }
//    @Test
//    fun `testing onSubscribe error`(){
//        var result: Throwable? = null
//        val mockException = mockk<FirebaseFirestoreException>()
//        val slot = slot<EventListener<QuerySnapshot>>()
//        every { mockUserNotes.addSnapshotListener(capture(slot)) } returns mockk()
//        provider.subscribeToNotes().observeForever{
//            result = (it as? NoteResult.Error)?.e
//        }
//        slot.captured.onEvent(null, mockException)
//        assertEquals(result, mockException)
//    }
//
//    @Test
//    fun `testing saveNote ok`(){
//        val mockDocRef = mockk<DocumentReference>()
//        every { mockUserNotes.document(testNotes[0].id) } returns mockDocRef
//        provider.setNote(testNotes[0])
//        verify(exactly = 1){ mockDocRef.set(testNotes[0])}
////      функция
////        userNotes.document(note.id)
////            .set(note)
////            .addOnSuccessListener {
////                value = NoteResult.Success(note)
////            }
////            .addOnFailureListener {
////                value = NoteResult.Error(it)
////            }
//    }
//    @Test
//    fun `testing saveNote note`() {
//        var resultNote: Note? = null
//        val slot = slot<OnSuccessListener<in Void>>()
//        every { mockUserNotes.document(testNotes[0].id)
//            .set(testNotes[0]).addOnSuccessListener(capture(slot)) } returns mockk()
//
//        provider.setNote(testNotes[0]).observeForever {
//            resultNote = (it as? Success<Note>)?.data
//        }
//        slot.captured.onSuccess(null)
//        assertEquals(resultNote, testNotes[0])
//    }
//    override fun getNote(id: String): LiveData<NoteResult> =
//        MutableLiveData<NoteResult>().apply {
//            try {
//                userNotes.document(id).get()
//                    .addOnSuccessListener {
//                        value = NoteResult.Success(it.toObject(Note::class.java))
//                    }
//                    .addOnFailureListener {
//                        value = NoteResult.Error(it)
//                    }
//            } catch (e: Throwable){
//                value = NoteResult.Error(e)
//            }
//        }
//    @Test
//    fun `testing getNote get`(){
//        val mockDocRef = mockk<DocumentReference>()
//        every { mockUserNotes.document(any()) } returns mockDocRef
//        provider.getNote("")
//        verify(exactly = 1) { mockDocRef.get() }
//    }
//    @Test
//    fun `testing getNote note`(){
//        var result: Note? = null
//        val mockDocRef = mockk<DocumentReference>()
//        val slot = slot<OnSuccessListener<DocumentSnapshot>>()
//
//        every { mockUserNotes.document(any()) } returns mockDocRef
//        every { mockDocRef.get().addOnSuccessListener(capture(slot)) } returns mockk()
//
//        provider.getNote(testNotes[0].id).observeForever{
//            result = (it as? Success<Note>)?.data
//        }
//        slot.captured.onSuccess(mockDoc1)
//        assertEquals(result, testNotes[0])
//    }
//    override fun deleteNote(noteId: String): LiveData<NoteResult> =
//        MutableLiveData<NoteResult>().apply {
//            try {
//                userNotes.document(noteId).delete()
//                    .addOnSuccessListener {
//                        value = NoteResult.Success(null)
//                    }.addOnFailureListener {
//                        value = NoteResult.Error(it)
//                    }
//            } catch (e: Throwable){
//                value = NoteResult.Error(e)
//            }
//        }
//    @Test
//    fun `testing deleteNote delete`(){
//        var result: Note? = testNotes[0]
//        val mockDocRef: DocumentReference = mockk()
//        val slot = slot<OnSuccessListener<in Void>>()
//
//        every { mockUserNotes.document(any()) } returns mockDocRef
//        every { mockDocRef.delete().addOnSuccessListener(capture(slot)) } returns mockk()
//
//        provider.deleteNote(testNotes[0].id).observeForever{
//            result = (it as? Success<Note>)?.data
//        }
//        slot.captured.onSuccess(null)
//        assert(result == null)
//    }
}