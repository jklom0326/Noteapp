package com.noteapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.noteapp.databinding.ActivityMainBinding
import com.noteapp.db.NoteDatabase
import com.noteapp.repository.NoteRepository
import com.noteapp.viewmodel.NoteViewModel
import com.noteapp.viewmodel.NoteViewModelProviderFactory

class MainActivity : AppCompatActivity() {
    // 바인딩 변수 선언
    private lateinit var binding: ActivityMainBinding

    // 뷰모델 선언
    lateinit var noteViewModel: NoteViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        setUpViewModel()
    }

    private fun setUpViewModel() {
        val noteRepository = NoteRepository(
            NoteDatabase(this)
        )
        val viewModelProviderFactory =
            NoteViewModelProviderFactory(
                application,
                noteRepository
            )

        noteViewModel = ViewModelProvider(
            this,
            viewModelProviderFactory
        ).get(NoteViewModel::class.java)
    }
}