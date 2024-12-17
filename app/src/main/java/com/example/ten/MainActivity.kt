package com.example.ten

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import com.example.ten.roomdb.NoteAndMindDataBasee
import com.example.ten.roomdb.NoteViewModel
import com.example.ten.ui.theme.TenTheme
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.ten.roomdb.Repository

class MainActivity : ComponentActivity() {

    private val db by lazy {
        Room.databaseBuilder(
            applicationContext,
            NoteAndMindDataBasee::class.java,
            name = "fuckyaaaaaaaaaaaaaaaaaaaaaaaa.db"
        ).build()
    }

    private val viewModel by viewModels<NoteViewModel>(
        factoryProducer = {
            object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return NoteViewModel(Repository(db)) as T
                }
            }
        }
    )


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TenTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    MMyApp(modifier = Modifier.padding(innerPadding),
                        viewModel = viewModel,
                    )
                }
            }
        }
    }
}

//添加 删除
//改变存入的图片格式


@Composable
fun MMyApp(modifier: Modifier,viewModel: NoteViewModel){


    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = "NotePage"
    ) {
        composable("NotePage"){ nootePage(modifier = modifier, viewModel = viewModel, navController = navController) }
        composable("pickPicPage"){ picPicPage(modifier = modifier, viewModel = viewModel, navController = navController) }

    }
}