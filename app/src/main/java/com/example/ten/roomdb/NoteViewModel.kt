package com.example.ten.roomdb

import android.graphics.Bitmap
import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch



class NoteViewModel(private val repository: Repository): ViewModel(){
    //通过点击 获取note 然后删除
    private var _DeletNote = Note("","",ByteArray(0),0)

    val SelectDeletNote: Note get() = _DeletNote

    fun setSelectDeleteNote (note : Note){
        Log.d("艹","NoteViewModel--setSelectDeleteNote--> $SelectDeletNote")
        _DeletNote = note
        setOutLineTextName(note.noteName)
        setOutLineTextBody(note.noteBody)
    }



    private var _ExpandState = false
    val ExpandStatee:Boolean get() = _ExpandState

    fun TurnOnOffExpand(){
        _ExpandState = ! _ExpandState
    }




    //输入框 title 的内容

    private var _NoteName = ""
    val OutLineTextName: String get() = _NoteName

    fun setOutLineTextName (noteName: String){
        _NoteName = noteName
        Log.d("艹","Noteviewmodel OutLineTextName--> $OutLineTextName")
    }



    //输入框 详细内容
    private var _NoteBody = ""
    val OutLineTextBody: String get() = _NoteBody


    fun setOutLineTextBody (noteBody: String){

        _NoteBody = noteBody
        Log.d("艹","Noteviewmodel setOutLineTextBody--> $OutLineTextBody")
    }




    //处理 图片
    //输入bitmap 输出 bytarray
    private var _pic = ByteArray(0)
    //private var _pic:List<ByteArray> = emptyList()

    //val SelectPic:List<ByteArray> get() = _pic
    val SelectPic:ByteArray get() = _pic


    fun setSelectpicToBytarray (bitmap: Bitmap){
        //_pic = emptyList()
        //var selectPicTranBitMapList =  ArrayList<ByteArray>()
        //Log.d("艹","noteViewModel--setSelectpicToBytarray--开始位置 检查selectPicTranBitMapList 是否为空 $selectPicTranBitMapList")
       // Log.d("艹","noteViewModel--setSelectpicToBytarray--开始位置 检查selectPic 是否为空 $SelectPic")

        //bitmaplist.forEach { singleBitMap->
            //输入bitmap 输出 bytarray

        val outputStream = java.io.ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 10, outputStream)

            //selectPicTranBitMapList.add(outputStream.toByteArray())
        //}
        //_pic = selectPicTranBitMapList

        _pic = outputStream.toByteArray()

        Log.d("艹","noteViewModel--setSelectpicToBytarray--结束位置 检查SelectPic $SelectPic")

    }
    fun resetSelectpicToBytarray(){
        _pic = ByteArray(0)
    }





    val allNote: Flow<List<Note>> = repository.getAllNotes()

    val allId: Flow<List<Int>> = repository.getAllID()

    fun getNoteById(id: Int) = repository.getNoteByID(id)


    fun upDateInformation(noteID: Int  ,noteName: String , noteBody: String , byteArray: ByteArray){
        viewModelScope.launch {
            repository.updateInformation(
                noteName = noteName,
                noteBody = noteBody,
                byteArray = byteArray,
                noteID = noteID
            )
        }
    }


    fun saveNoteImage(noteName: String , noteBody: String , byteArray: ByteArray) {
        viewModelScope.launch {
            repository.saveNoteImage(noteName,noteBody,byteArray)
        }
    }

    fun deleteNote(note: Note){
        viewModelScope.launch {
            repository.deleteNote(note)
        }
    }





}