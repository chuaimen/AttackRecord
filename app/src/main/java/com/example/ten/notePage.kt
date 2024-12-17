package com.example.ten

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.wear.compose.material.ContentAlpha
import com.example.ten.roomdb.Note
import com.example.ten.roomdb.NoteViewModel


@Composable
fun nootePage(modifier: Modifier,
              viewModel: NoteViewModel,
              navController: NavHostController) {


    var name by remember {
        mutableStateOf("")
    }


    var body by remember {
        mutableStateOf("")
    }

    //重新编写 读取数据库 读取 id name body
    val dataList by viewModel.allNote.collectAsState(initial = emptyList())
    Log.d("艹", "notepage--开始位置 >> 数据流读取 是否单次读取$dataList")
    Log.d(
        "艹", "notepage--开始位置 >> viewmodel 数据流读取 name >>>> ${viewModel.OutLineTextName}" +
                "   |body >>>> ${viewModel.OutLineTextBody}" +
                "  |pic >>>> ${viewModel.SelectPic}"
    )


    Column(modifier = Modifier.padding(10.dp)) {


        LazyColumn(modifier = Modifier.height(640.dp)) {
            Log.d("艹", "notePage-lazycolumn-")
            items(dataList.reversed()) { list ->


                Log.d("艹", "notePage-lazycolumn expand >> $list")
                if (list.noteName.contains(name)) {

                    Column {

                        var expandedState by remember { mutableStateOf(false) }
                        val rotationState by animateFloatAsState(targetValue = if (expandedState) 180f else 0f)

                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .animateContentSize(
                                    animationSpec = tween(
                                        durationMillis = 300,
                                        easing = LinearOutSlowInEasing
                                    )
                                ),

                            onClick = {
                                expandedState = !expandedState
                                viewModel.setSelectDeleteNote(list)
                                name = list.noteName
                                body = list.noteBody

                                Log.d("艹", "expandpage >> viewmodel expandedState $expandedState")
                            }
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(5.dp)
                            ) {

                                Row(verticalAlignment = Alignment.CenterVertically) {

                                    Text(
                                        modifier = Modifier.weight(6f),
                                        text = list.noteName,
                                        overflow = TextOverflow.Ellipsis
                                    )

                                    IconButton(
                                        modifier = Modifier
                                            .weight(1f)
                                            .alpha(ContentAlpha.medium)
                                            .rotate(rotationState),
                                        onClick = { expandedState = !expandedState }
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.ArrowDropDown,
                                            contentDescription = "Drop-Down Arrow"
                                        )
                                    }
                                }



                                if (expandedState) {
                                    Column {
                                        Text(text = list.noteBody)

                                        val bitmap =
                                            BitmapFactory.decodeByteArray(
                                                list.imageData,
                                                0,
                                                list.imageData.size
                                            )

                                        bitmap?.asImageBitmap()?.let { imageBitmap ->
                                            Image(
                                                bitmap = imageBitmap,
                                                contentDescription = "Image from ByteArray",
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                    Divider(modifier = Modifier.padding(10.dp))
                } else if (list.noteName == "") {

                    Column {

                        var expandedState by remember { mutableStateOf(false) }
                        val rotationState by animateFloatAsState(targetValue = if (expandedState) 180f else 0f)

                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .animateContentSize(
                                    animationSpec = tween(
                                        durationMillis = 300,
                                        easing = LinearOutSlowInEasing
                                    )
                                ),

                            onClick = {
                                expandedState = !expandedState
                                viewModel.setSelectDeleteNote(list)
                                name = list.noteName
                                body = list.noteBody

                                Log.d("艹", "expandpage >> viewmodel expandedState $expandedState")
                            }
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(5.dp)
                            ) {

                                Row(verticalAlignment = Alignment.CenterVertically) {

                                    Text(
                                        modifier = Modifier.weight(6f),
                                        text = list.noteName,
                                        overflow = TextOverflow.Ellipsis
                                    )

                                    IconButton(
                                        modifier = Modifier
                                            .weight(1f)
                                            .alpha(ContentAlpha.medium)
                                            .rotate(rotationState),
                                        onClick = { expandedState = !expandedState }
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.ArrowDropDown,
                                            contentDescription = "Drop-Down Arrow"
                                        )
                                    }
                                }



                                if (expandedState) {
                                    Column {
                                        Text(text = list.noteBody)

                                        val bitmap =
                                            BitmapFactory.decodeByteArray(
                                                list.imageData,
                                                0,
                                                list.imageData.size
                                            )

                                        bitmap?.asImageBitmap()?.let { imageBitmap ->
                                            Image(
                                                bitmap = imageBitmap,
                                                contentDescription = "Image from ByteArray",
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                    Divider(modifier = Modifier.padding(10.dp))

                }else {
                    Log.d("ggg Lazy noteName Not good(333) ", "${list}")
                }
            }
        }

        // name 输入
        Spacer(modifier = Modifier.padding(15.dp))

        Row {
            Button(

                onClick = {
                    Log.d(
                        "艹",
                        "notepage--输入按钮 |name >>>> ${viewModel.OutLineTextName}" +
                                "   |body >>>> ${viewModel.OutLineTextBody}" +
                                "  |pic >>>> ${viewModel.SelectPic}"
                    )


                    viewModel.saveNoteImage(
                        viewModel.OutLineTextName,
                        viewModel.OutLineTextBody,
                        viewModel.SelectPic
                    )


                    name = ""
                    body = ""
                    viewModel.setOutLineTextName("")
                    viewModel.setOutLineTextBody("")
                    viewModel.resetSelectpicToBytarray()
                }
            ) {
                Text(text = "进")

            }

            Spacer(modifier = Modifier.width(2.dp))


            Button(onClick = {
                Log.d("艹", "notePage-删除建-${viewModel.SelectDeletNote}")
                viewModel.deleteNote(viewModel.SelectDeletNote)
                viewModel.TurnOnOffExpand()
                name = ""
                body = ""
                viewModel.setOutLineTextName("")
                viewModel.setOutLineTextBody("")
                viewModel.resetSelectpicToBytarray()
            }) {
                Text(text = "删")
            }

            Spacer(modifier = Modifier.width(2.dp))

            Button(
                onClick = {
                    navController.navigate("pickPicPage")
                }) {
                Text(text = "pic")
            }

            Spacer(modifier = Modifier.width(2.dp))

            Button(onClick = {
                name = ""
                body = ""
                viewModel.setOutLineTextName("")
                viewModel.setOutLineTextBody("")
                viewModel.resetSelectpicToBytarray()
            }) {
                Text(text = "清")
            }

            Spacer(modifier = Modifier.width(2.dp))

            Button(onClick = {
                viewModel.upDateInformation(
                    noteID = viewModel.SelectDeletNote.noteId,
                    noteName = viewModel.OutLineTextName,
                    noteBody = viewModel.OutLineTextBody,
                    byteArray = viewModel.SelectDeletNote.imageData
                )

                name = ""
                body = ""
                viewModel.setOutLineTextName("")
                viewModel.setOutLineTextBody("")
                viewModel.resetSelectpicToBytarray()
            }) {
                Text(text = "up")
            }

        }



        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .height(55.dp)
                .clip(shape = RoundedCornerShape(1))
                .background(Color.White),
            value = name,
            onValueChange = {
                name = it
                viewModel.setOutLineTextName(name)
                Log.d(
                    "艹",
                    "notepage--nameOutLine  |name >>>> ${viewModel.OutLineTextName}" +
                            "   |body >>>> ${viewModel.OutLineTextBody}" +
                            "  |pic >>>> ${viewModel.SelectPic}"
                )
            },
            placeholder = {
                Text(text = "TiTle", color = Color.LightGray)
            })


        // Body 输入
        Spacer(modifier = Modifier.padding(5.dp))

        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .clip(shape = RoundedCornerShape(1))
                .background(Color.White),
            value = body,
            onValueChange = {
                body = it
                viewModel.setOutLineTextBody(body)
                Log.d(
                    "艹",
                    "notepage bodyOutline  |name >>>> ${viewModel.OutLineTextName}" +
                            "   |body >>>> ${viewModel.OutLineTextBody}" +
                            "  |pic >>>> ${viewModel.SelectPic}"
                )
            },
            placeholder = {
                Text(text = "填写内容", color = Color.LightGray)
            })





    }
}



@Composable
fun LoadingColumn(modifier: Modifier, viewModel: NoteViewModel, list: Note, whichONe: Int) {
    Column(modifier = Modifier.clickable {
        viewModel.setSelectDeleteNote(list)
    }) {

        var expandedState by remember { mutableStateOf(false) }
        val rotationState by animateFloatAsState(
            targetValue = if (expandedState) 180f else 0f
        )

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .animateContentSize(
                    animationSpec = tween(
                        durationMillis = 300,
                        easing = LinearOutSlowInEasing
                    )
                ),
            //shape = ,
            onClick = {
                expandedState = !expandedState
                Log.d("艹", "expandpage >> viewmodel expandedState $expandedState")
            }
        ) {

            if (expandedState) {


                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(5.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            modifier = Modifier.weight(6f),
                            text = list.noteName,
                            overflow = TextOverflow.Ellipsis
                        )

                        IconButton(
                            modifier = Modifier
                                .weight(1f)
                                .alpha(ContentAlpha.medium)
                                .rotate(rotationState),
                            onClick = { expandedState = !expandedState }) {
                            Icon(
                                imageVector = Icons.Default.ArrowDropDown,
                                contentDescription = "Drop-Down Arrow"
                            )
                        }
                    }



                    if (expandedState) {
                        Column {
                            Text(text = list.noteBody)

                            val bitmap =
                                BitmapFactory.decodeByteArray(list.imageData, 0, list.imageData.size)

                            bitmap?.asImageBitmap()?.let { imageBitmap ->
                                Image(
                                    bitmap = imageBitmap,
                                    contentDescription = "Image from ByteArray",
                                )
                            }
                        }
                    }
                }
            }
        }
    }
        Divider(modifier = Modifier.padding(10.dp))
}
