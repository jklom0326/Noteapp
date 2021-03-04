package com.noteapp.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.noteapp.model.Note

@Dao
interface NoteDao {

    @Insert
    suspend fun addNote(note: Note)

    @Update
    suspend fun updateNote(note: Note)

    @Delete
    suspend fun deleteNote(note: Note)

    @Query("SELECT * FROM notes ORDER BY id DESC")
    fun getAllNotes(): LiveData<List<Note>>
}