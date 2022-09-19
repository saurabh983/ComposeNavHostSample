package com.gl.hindustantimesrss.ui.details

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.ExperimentalUnitApi
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.gl.hindustantimesrss.R
import com.gl.hindustantimesrss.models.NewsItems
import com.gl.hindustantimesrss.ui.theme.black
import com.gl.hindustantimesrss.ui.theme.gray
import com.gl.hindustantimesrss.ui.theme.white
import com.gl.hindustantimesrss.ui.theme.whiteGray

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class, ExperimentalUnitApi::class)
@Composable
fun NewsDetails(
    newsItems: NewsItems?,
    navigateUp: () -> Unit
) {
    val darkTheme = isSystemInDarkTheme()
    Scaffold(containerColor = if (darkTheme) black else white, topBar = {
        TopAppBar(
            title = {
                Text(
                    modifier = Modifier.padding(start = 16.dp),
                    text = "Details",
                    color = white
                )
            },
            colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = if (darkTheme) gray else whiteGray),
            navigationIcon = {
                Image(painter = painterResource(id = R.drawable.ic_round_arrow_back_24),
                    contentDescription = "BackIcon",
                    Modifier
                        .padding(start = 10.dp)
                        .clickable {
                            navigateUp.invoke()
                        })
            }
        )
    }, content = {
        Column(modifier = Modifier.fillMaxSize()) {

            AsyncImage(
                modifier = Modifier.requiredSizeIn(minHeight = 350.dp),
                model = newsItems!!.media.content[0].url[0], contentDescription = "NewsImage"
            )

            Text(modifier = Modifier.padding(start = 16.dp, end = 16.dp),
                text = newsItems.title,
                color = if (darkTheme) white else black,
                fontSize = TextUnit(value = 20f, type = TextUnitType.Sp),
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.size(20.dp))

            Text(modifier = Modifier.padding(start = 16.dp, end = 16.dp),
                text = newsItems.description,
                color = if (darkTheme) white else black,
                fontSize = TextUnit(value = 16f, type = TextUnitType.Sp),
                fontWeight = FontWeight.Normal
            )

            val uriHandler = LocalUriHandler.current
            OutlinedButton(modifier = Modifier.align(alignment = Alignment.CenterHorizontally).weight(1f).requiredSizeIn(
                minHeight = 48.dp,
                minWidth = 200.dp,
                maxHeight = 48.dp,
                maxWidth = 200.dp
            ),
                shape = RoundedCornerShape(8.dp), onClick = {
                    uriHandler.openUri(newsItems.url)
                }) {
                Text(text = "View in browser", color = if (darkTheme) Color.Cyan else Color.Red)
            }

        }
    })
}