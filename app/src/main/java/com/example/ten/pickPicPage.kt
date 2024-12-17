package com.example.ten

/*
添加 log.d 跟踪-------
logic-chain >>> pickpicPage 打开 单选/多选 -bitmap-> viewModel.setSelectpicBytarray --> room
统一格式 bitmap(bytarray?)         context -单bitmap->viewModel.setSelectpicBytarray -单bytarray->room
           单选/多选?    |List<单/多->List<bitmap>>|->FOREACH<viewModel.setSelectpicBytarray> -List<bytarray>-> room
多选foreach(单选)<位置 page/viewModel?>
 */


import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.AbsoluteAlignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.ten.roomdb.Note
import com.example.ten.roomdb.NoteViewModel


@Composable
fun picPicPage(viewModel: NoteViewModel,modifier: Modifier, navController: NavHostController){
    val context = LocalContext.current
    var selectImageUri = remember{
        mutableStateOf<Uri?>(null)
    }
    var selectImageUriList = remember{
        mutableStateOf<List<Uri?>>(emptyList())
    }




    var imageByteArray:ByteArray? = ByteArray(0)

    val singleImagePickerLaunch = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = {
                Uri->
            selectImageUri.value = Uri
            Log.d("艹","pickpage-> 开始位置 -> 单选 -> $selectImageUri.value")



        }
    )

    val MutipleImagePickerLaunch = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickMultipleVisualMedia(),
        onResult = {
                UriList->
            selectImageUriList.value = UriList
            Log.d("艹","pickpage-> 开始位置 -> 多选 -> $selectImageUriList.value")
        }
    )


    Column(modifier = Modifier
        .fillMaxWidth()
    ) {


        //-优化- pic 页面选中 为添加模式，选错-> 删改 或直接 覆盖重选
        // 选中的照片 需要 跟显式的 进行同步
        //开始位置 判断是否  为空
        //var selectPicTranBitMapList =  ArrayList<Bitmap>()
        //Log.d("艹","pickpage->Lazycolumn-> 开始位置 -> $selectPicTranBitMapList")
        //Log.d("艹","pickpage->Lazycolumn-> 开始位置 -> $viewModel.SelectPic")
        //Log.d("艹","pickpage->Lazycolumn-> 开始位置 -> selectPicTranBitMapList == viewModel.SelectPic >>> ${selectPicTranBitMapList == viewModel.SelectPic}")

        LazyColumn(modifier = Modifier.height(800.dp)) {


            item {

                AsyncImage(
                    model = selectImageUri.value,
                    contentDescription = "",
                    Modifier.fillMaxWidth(),
                    contentScale = ContentScale.Crop
                )
                Divider(
                    Modifier
                        .fillMaxWidth())

                //selectPicTranBitMapList.add(TranIntoBytarry(viewModel = viewModel,contex = context, uri = selectImageUri.value))
                //Log.d("艹","pickpage->Lazycolumn 单个item 最后的 selectPicTranBitMapList $selectPicTranBitMapList")

                // 针对 selectImageUri.value 转换为 list<bitmap> 传入 viewmodel
                //重选 图片数据是否 始终与viewmodel 保持一致。两个位置 开始结束
                if (Build.VERSION.SDK_INT < 28) {
                    MediaStore.Images.Media.getBitmap(context.contentResolver, selectImageUri.value)
                    Log.d("艹","pickpage->Lazycolumn-> 单个item >>ELSE<< Build.VERSION.SDK_INT < 28 ")
                } else {
                    val source =
                        selectImageUri.value?.let {
                            ImageDecoder.createSource(context.contentResolver,
                                it
                            )
                        }
                    if (source != null) {
                        //imageBitmap = ImageDecoder.decodeBitmap(source)
                        // ※※※※※
                        //selectPicTranBitMapList.add(ImageDecoder.decodeBitmap(source))
                        viewModel.setSelectpicToBytarray(ImageDecoder.decodeBitmap(source))

                    } else {
                        Log.d("艹","pickpage->Lazycolumn->单个item soured is null  ")
                    }
                    Log.d("艹","pickpage->Lazycolumn->单个item >>ELSE<< Build.VERSION.SDK_INT > 28 ")
                }

                Log.d("艹","pickpage->Lazycolumn 单个item 最后的 viewModel.SelectPic  ${viewModel.SelectPic}")


                //viewModel.setSelectpicToBytarray(selectPicTranBitMapList)



            }
            items(selectImageUriList.value){
                    uri ->
                AsyncImage(
                    model = uri,
                    contentDescription = "",
                    Modifier.fillMaxWidth(),
                    contentScale = ContentScale.Crop
                )
                Divider(
                    Modifier
                        .fillMaxWidth()
                        .padding(8.dp))
                Log.d("inphoto","$uri")
            }


        }






        Spacer(modifier = Modifier.height(10.dp))
        Row (
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)
        ){
            Button(onClick = {

                singleImagePickerLaunch.launch(
                    PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                )

            }) {
                Text(text = "Pic")
            }
            /*
            Button(onClick = {

                MutipleImagePickerLaunch.launch(
                    PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                )

            }) {
                Text(text ="多选" )
            }

             */

            Button(
                modifier = Modifier
                    .padding(horizontal = 100.dp),
                onClick = {
                    navController.navigate("NotePage")
                }) {
                Text(text = "O")

            }
        }









    }



}


@Composable
fun AllSelectPic(){
    Column {
        // lazycolumn
    }
}




@Composable
fun TranIntoBytarry(viewModel: NoteViewModel, contex : Context, uri : Uri?) {

    // 将Uri转换为Bitmap
    var imageBitmap: Bitmap? = null
    //var imageByteArray:ByteArray? = ByteArray(0)

    if (Build.VERSION.SDK_INT < 28) {
        MediaStore.Images.Media.getBitmap(contex.contentResolver, uri)
        Log.d("ggg inPickPicImage"," >>IF<< Build.VERSION.SDK_INT < 28 ")
    } else {
        val source =
            uri?.let {
                ImageDecoder.createSource(contex.contentResolver,
                    it
                )
            }
        if (source != null) {
            imageBitmap = ImageDecoder.decodeBitmap(source)
            // ※※※※※


        } else {
            Log.d("艹","pickpage->Lazycolumn-> TranIntoBytarry  ")
            Log.d("ggg pickpicPage","soured is null")
        }
        Log.d("艹","pickpage->Lazycolumn-> TranIntoBytarry >>ELSE<< Build.VERSION.SDK_INT > 28 ")
    }



    // 将Bitmap转换为ByteArray
    // val outputStream = ByteArrayOutputStream()
    //imageBitmap?.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
    //imageByteArray = outputStream.toByteArray()

    //Log.d("艹","pickpage->Lazycolumn-> TranIntoBytarry $imageByteArray")
}