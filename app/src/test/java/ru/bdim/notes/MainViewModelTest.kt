package ru.bdim.notes

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertFalse
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import ru.bdim.notes.model.Note
import ru.bdim.notes.model.NoteResult
import ru.bdim.notes.model.Repository
import ru.bdim.notes.viewmodel.MainViewModel

class MainViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private lateinit var viewModel: MainViewModel
    private val mockRepository = mockk<Repository>()
    private val liveData = MutableLiveData<NoteResult>()

    @Before
    fun start(){
        clearAllMocks()
        every { mockRepository.getNotes() } returns liveData
        viewModel = MainViewModel(mockRepository)

    }
    @Test
    fun `testing repository get notes`(){
        verify (exactly = 1) { mockRepository.getNotes() }
    }
    @Test
    fun `testing setting notes`(){
        var result: List<Note>? = null
        val testNotes = listOf(Note("a"), Note("b"))
        viewModel.viewStateLD.observeForever{
            result = it?.data
        }
        liveData.value = NoteResult.Success<List<Note>>(testNotes)
        assertEquals(result, testNotes)
    }
    @Test
    fun `testing setting notes with error`(){
        var result: Throwable? = null
        val testNotes = Throwable("my error")
        viewModel.viewStateLD.observeForever{
            result = it?.e
        }
        liveData.value = NoteResult.Error(testNotes)
        assertEquals(result, testNotes)
    }
    @Test
    fun `testing removing observer`(){
        with (viewModel.javaClass.getDeclaredMethod("onCleared")){
            isAccessible = true
            invoke(viewModel)
        }
        assertFalse(liveData.hasObservers())
    }
}