package ru.bdim.notes

import android.content.Intent
import androidx.lifecycle.MutableLiveData
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.intent.rule.IntentsTestRule
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.koin.android.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module
import org.koin.standalone.StandAloneContext.loadKoinModules
import org.koin.standalone.StandAloneContext.stopKoin
import ru.bdim.notes.model.Note
import ru.bdim.notes.model.NoteViewState
import ru.bdim.notes.view.NoteActivity
import ru.bdim.notes.viewmodel.NoteViewModel

class NoteActivityTest {
    @get:Rule
    val rule = IntentsTestRule(
        NoteActivity::class.java, true, false)

    private val mockViewModel = mockk<NoteViewModel>(relaxed = true)
    private val liveData = MutableLiveData<NoteViewState>()

    private val testNote = Note("1", "test", "самый важный", Note.NoteColor.YELLOW)

    @Before
    fun setup(){
        loadKoinModules(listOf(
            module { viewModel (override = true){ mockViewModel } }
        ))
        every { mockViewModel.getViewState() } returns liveData
        Intent().apply {
            putExtra("TAG", testNote.id).let {
                rule.launchActivity(it)
            }
        }
    }

    @Test
    fun testing_initView(){
        liveData.postValue(NoteViewState(NoteViewState.Data(note = testNote)))
        onView(withId(R.id.etx_note_title)).check(matches(withText(testNote.title)))
        onView(withId(R.id.etx_note_body)).check(matches(withText(testNote.text)))
    }

    @Test
    fun testing_delete_note(){
        openActionBarOverflowOrOptionsMenu(rule.activity)
        onView(withText(R.string.delete)).perform(click())
        onView(withText(R.string.yes)).perform(click())
        verify (exactly = 1) { mockViewModel.deleteNote() }
    }

    @After
    fun stop(){
        stopKoin()
    }
}