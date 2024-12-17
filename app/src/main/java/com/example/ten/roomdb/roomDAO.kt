package com.example.ten.roomdb

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow


@Dao
interface roomDAO{
    @Upsert
    suspend fun upsertNote(note: Note)

    @Delete
    suspend fun deleNote(not:Note)

    //主要功能
    @Insert
    suspend fun insertNoteImage(image: Note)

    @Update
    suspend fun updateInfomation(note: Note)



    @Query("SELECT * FROM Note")
    fun getAllNotes(): Flow<List<Note>>



    @Query("SELECT * FROM Note WHERE noteId = :id")
    fun getnotelistById(id: Int): Note


    @Query("SELECT noteId FROM note")
    fun getId():Flow<List<Int>>




}