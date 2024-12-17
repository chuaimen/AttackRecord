package com.example.ten.roomdb

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Note::class],version = 1)
abstract class NoteAndMindDataBasee: RoomDatabase(){

    abstract val dao: roomDAO
}