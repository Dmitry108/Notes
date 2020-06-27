package ru.bdim.notes.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import com.firebase.ui.auth.AuthUI
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.viewmodel.ext.android.viewModel
import ru.bdim.notes.R
import ru.bdim.notes.model.Note
import ru.bdim.notes.model.MainViewState
import ru.bdim.notes.viewmodel.MainViewModel

class MainActivity : BaseActivity<List<Note>?, MainViewState>() {

    companion object{
        fun start(context: Context){
            context.startActivity(Intent(context, MainActivity::class.java))
        }
    }
    override val viewModel: MainViewModel by viewModel()
//    lazy {
//        ViewModelProviders.of(this).get(MainViewModel::class.java) }
    override val layoutId: Int = R.layout.activity_main
    private lateinit var adapter: NotesRecyclerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSupportActionBar(tlb_main)

        adapter = NotesRecyclerAdapter{
            NoteActivity.startActivity(this, it.id)
        }
        rcw_notes.adapter = adapter

        fab_note.setOnClickListener{
            NoteActivity.startActivity(this)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean =
        MenuInflater(this).inflate(R.menu.logout, menu).let { true }

    override fun onOptionsItemSelected(item: MenuItem): Boolean =
        when(item.itemId){
            R.id.mnu_logout -> showExitDialog().let{ true }
            else -> false
        }
    override fun renderData(data: List<Note>?) {
        data?.let {
        adapter.notes = it }
    }
    fun showExitDialog(){
        supportFragmentManager.findFragmentByTag(LogoutDialog.TAG) ?:
                LogoutDialog.create { onLogout() }.show(supportFragmentManager, LogoutDialog.TAG)
    }
    fun onLogout(){
        AuthUI.getInstance().signOut(this).addOnCompleteListener{
            startActivity(Intent(this, SplashActivity::class.java))
            finish()
        }
    }
}