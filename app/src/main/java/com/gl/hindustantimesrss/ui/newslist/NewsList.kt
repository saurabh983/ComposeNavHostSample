package com.gl.hindustantimesrss.ui.newslist

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import coil.compose.AsyncImage
import com.gl.hindustantimesrss.R
import com.gl.hindustantimesrss.models.MainResponse
import com.gl.hindustantimesrss.models.NewsItems
import com.gl.hindustantimesrss.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewsList(loading: Boolean = false, newsResponse: MainResponse?, errorPair: Pair<Boolean, String>,
             selectedNews: (NewsItems) -> Unit, refresh: () -> Unit) {
    val darkTheme = isSystemInDarkTheme()
    Scaffold(containerColor = if (darkTheme) black else white, topBar = {
        TopAppBar(
            title = { Text(text = "Hindustan Times: India", color = white) },
            colors = TopAppBarDefaults.smallTopAppBarColors(containerColor =  if (darkTheme) gray else whiteGray),
            actions = {
                Image(painter = painterResource(id = R.drawable.ic_round_refresh_24)
                    , contentDescription = "RefreshIcon", modifier = Modifier.padding(end = 8.dp).clickable {
                        refresh.invoke()
                    })
            }
        )
    }, content = {
        if (!loading && !errorPair.first) {
            LazyColumn(
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.padding(top = it.calculateTopPadding()),
                content = {
                    items(newsResponse!!.items.size) { index ->
                        Card(
                            modifier = Modifier
                                .requiredSizeIn(
                                    minHeight = 200.dp,
                                    maxHeight = 200.dp
                                ),
                            elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
                            shape = RoundedCornerShape(8.dp),
                            colors = CardDefaults.cardColors(containerColor = gray),
                            onClick = {
                                selectedNews.invoke(newsResponse.items[index])
                            }
                        ) {

                            ConstraintLayout(modifier = Modifier.fillMaxSize()) {
                                val (title) = createRefs()
                                AsyncImage(
                                    model = newsResponse.items[index].media.content[0].url[0],
                                    contentDescription = "UserImage",
                                    modifier = Modifier
                                        .fillMaxSize()
                                )
                                Text(
                                    modifier = Modifier
                                        .background(brush = verticalGradientBrush)
                                        .padding(8.dp)
                                        .constrainAs(title) {
                                            bottom.linkTo(parent.bottom)
                                            start.linkTo(parent.start)
                                            end.linkTo(parent.end)
                                        },
                                    text = newsResponse!!.items[index].title,
                                    color = white
                                )
                            }
                        }
                    }

                })
        }
        else {
            Column(modifier= Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center) {
                if (errorPair.first){
                    OutlinedButton(modifier = Modifier
                        .fillMaxWidth()
                        .requiredSizeIn(maxHeight = 70.dp)
                        .align(alignment = Alignment.CenterHorizontally)
                        .weight(1f),
                        shape = RoundedCornerShape(8.dp), onClick = {
                            refresh.invoke()
                        }) {
                        Text(text = errorPair.second+" : Click here to Reload", color = if (darkTheme) Color.Cyan else Color.Red)
                    }
                }
                else {
                    Text(text = "Loading large\nnumber of news...", color = if (darkTheme) white else black, modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center)
                }

            }
        }
    })
}