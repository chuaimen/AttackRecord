package com.example.ten.roomdb

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Note(
    val noteName : String,
    val noteBody : String,
    val imageData: ByteArray,
    @PrimaryKey(autoGenerate = true)
    val noteId: Int = 0
)
