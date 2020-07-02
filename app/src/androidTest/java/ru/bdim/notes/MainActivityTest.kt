package ru.bdim.notes

import androidx.lifecycle.MutableLiveData
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions.scrollToPosition
import androidx.test.espresso.intent.rule.IntentsTestRule
import androidx.test.espresso.matcher.ViewMatchers.*
import io.mockk.every
import io.mockk.mockk
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.koin.android.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module
import org.koin.standalone.StandAloneContext.loadKoinModules
import org.koin.standalone.StandAloneContext.stopKoin
import ru.bdim.notes.model.MainViewState
import ru.bdim.notes.model.Note
import ru.bdim.notes.view.MainActivity
import ru.bdim.notes.view.NotesRecyclerAdapter
import ru.bdim.notes.viewmodel.MainViewModel

class MainActivityTest {
//
//    @get:Rule
//    val rule = IntentsTestRule(MainActivity::class.java,
//        true, false)
//
//    private val mockViewModel = mockk<MainViewModel>(relaxed = true)
//    private val liveData = MutableLiveData<MainViewState>()
//
//    private val testNotes = listOf(Note("1", "title", "text", Note.NoteColor.ORANGE),
//    Note("2", "log", "sol-6", Note.NoteColor.GREEN))
//
//    @Before
//    fun start(){
//        loadKoinModules(listOf(module {
//            viewModel(override = true) { mockViewModel }
//        }))
//        //every { mockViewModel.getViewState() } returns liveData
//        rule.launchActivity(null)
//        liveData.postValue(MainViewState(notes = testNotes))
//    }
//
//    @Test
//    fun recycler_test(){
//        onView(withId(R.id.rcw_notes))
//            .perform(scrollToPosition<NotesRecyclerAdapter.NoteViewHolder>(2))
//        onView(withText(testNotes[1].text)).check(matches(isDisplayed()))
//    }
//
//    @After
//    fun stop(){
//        stopKoin()
//    }
}