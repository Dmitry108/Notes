package ru.bdim.notes

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import junit.framework.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import ru.bdim.notes.model.Note
import ru.bdim.notes.model.NoteResult
import ru.bdim.notes.model.NoteViewState
import ru.bdim.notes.model.Repository
import ru.bdim.notes.viewmodel.NoteViewModel

class NoteViewModelTest {
//    @get:Rule
//    val rule = InstantTaskExecutorRule()
//
//    private lateinit var viewModel: NoteViewModel
//
//    private val mockRepository: Repository = mockk(relaxed = true)
//    private val liveData = MutableLiveData<NoteResult>()
//    private val testNote = Note("a")
//
//    @Before
//    fun start(){
//        clearAllMocks()
//        every { mockRepository.getNote(testNote.id) } returns liveData
//        every { mockRepository.deleteNote(testNote.id) } returns liveData
//        viewModel = NoteViewModel(mockRepository)
//    }
//    @Test
//    fun `testing loadNote success`(){
//        var result: NoteViewState.Data? = null
//        val testData = NoteViewState.Data(false, testNote)
//        viewModel.viewStateLD.observeForever{
//            result = it?.data
//        }
//        viewModel.loadNote(testNote.id)
//        liveData.value = NoteResult.Success(testNote)
//        assertEquals(testData, result)
//    }
//    @Test
//    fun `testing loadNote error`(){
//        var result: Throwable? = null
//        val testError = Throwable("iii")
//        viewModel.viewStateLD.observeForever{
//            result = it?.e
//        }
//        viewModel.loadNote(testNote.id)
//        liveData.value = NoteResult.Error(testError)
//        assertEquals(testError, result)
//    }
//    @Test
//    fun `testing deleteNote success`(){
//        var result: NoteViewState.Data? = null
//        val testData = NoteViewState.Data(true)
//        viewModel.viewStateLD.observeForever{
//            result = it?.data
//        }
//        viewModel.saveNote(testNote)
//        viewModel.deleteNote()
//        liveData.value = NoteResult.Success(null)
//        assertEquals(testData, result)
//    }
//    @Test
//    fun `testing deleteNote error`(){
//        var result: Throwable? = null
//        val testError = Throwable("iii")
//        viewModel.viewStateLD.observeForever{
//            result = it?.e
//        }
//        viewModel.saveNote(testNote)
//        viewModel.deleteNote()
//        liveData.value = NoteResult.Error(testError)
//        assertEquals(testError, result)
//    }
//    @Test
//    fun `testing saving note`(){
//        viewModel.saveNote(testNote)
//        with(viewModel.javaClass.getDeclaredMethod("onCleared")){
//            isAccessible = true
//            invoke(viewModel)
//        }
//        verify(exactly = 1){ mockRepository.setNote(testNote) }
//    }
}