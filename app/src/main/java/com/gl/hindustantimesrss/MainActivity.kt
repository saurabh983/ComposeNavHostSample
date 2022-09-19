package com.gl.hindustantimesrss

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import coil.compose.AsyncImage
import com.gl.hindustantimesrss.navigation.AppNavigation
import com.gl.hindustantimesrss.ui.theme.*
import com.gl.hindustantimesrss.viewmodel.NewViewModel

class MainActivity : ComponentActivity() {

    private val viewModel: NewViewModel by viewModels()

    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycle.addObserver(viewModel)
        setContent {
            HindustanTimesRssTheme {
                // A surface container using the 'background' color from the theme
                val newsResponse by viewModel.newsResponse.observeAsState()
                val loading by viewModel.loading.observeAsState(true)
                val errorPair by viewModel.error.observeAsState(Pair(false, ""))

                AppNavigation(newsResponse = newsResponse, loading = loading, errorPair = errorPair,
                refresh = {
                    viewModel.getNewsArticles()
                })
            }
        }
    }
}