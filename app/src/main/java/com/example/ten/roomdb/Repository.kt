package com.example.ten.roomdb


class Repository(private val db:  NoteAndMindDataBasee){
    suspend fun upsertNote(note: Note){
        db.dao.upsertNote(note)

    }

    suspend fun deleteNote(note: Note){
        db.dao.deleNote(note)
    }

    suspend fun updateInformation(noteName: String,noteBody: String, byteArray: ByteArray, noteID: Int){
        db.dao.updateInfomation(Note(noteName, noteBody, imageData = byteArray, noteID))
    }

    //suspend fun saveNoteImage(noteName: String,noteBody: String,bitmap: android.graphics.Bitmap) {
    suspend fun saveNoteImage(noteName: String,noteBody: String, byteArray: ByteArray){
        /*
        val outputStream = java.io.ByteArrayOutputStream()
        try {
            bitmap.compress(Bitmap.CompressFormat.JPEG, 10, outputStream)
            Log.d("ggg in Repository", "save JPEG pic")
        }catch (e: ArithmeticException){
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
            Log.d("ggg in Repository", "save PNG pic")
        }

        val byteArray = outputStream.toByteArray()

         */
        db.dao.insertNoteImage(Note(noteName, noteBody, imageData = byteArray))
        //db.dao.insertNoteImage(Note())


    }

    fun getAllNotes() = db.dao.getAllNotes()

    fun getAllID() = db.dao.getId()

    fun getNoteByID(id: Int) = db.dao.getnotelistById(id)


}