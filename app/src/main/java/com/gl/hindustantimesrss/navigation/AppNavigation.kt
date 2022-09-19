package com.gl.hindustantimesrss.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.gl.hindustantimesrss.models.MainResponse
import com.gl.hindustantimesrss.models.NewsItems
import com.gl.hindustantimesrss.navigation.AppDestinations.NEWS_DATA_KEY
import com.gl.hindustantimesrss.ui.details.NewsDetails
import com.gl.hindustantimesrss.ui.newslist.NewsList
import com.google.gson.Gson

private object AppDestinations {
    const val NEWS_LIST = "newsList"
    const val NEWS_DETAILS = "newsDetails?newsData={newsData}"
    const val NEWS_DATA_KEY = "newsData"
}

private class AppActions(navController: NavHostController) {
    val selectedNewsItems: (NewsItems) -> Unit = { newsItems ->
        navController.navigate(AppDestinations.NEWS_DETAILS
            .replace("{newsData}", Gson().toJson(newsItems)))
    }

    val navigateUp: () -> Unit = {
        navController.navigateUp()
    }
}

@Composable
fun AppNavigation(
    loading: Boolean = false,
    newsResponse: MainResponse?,
    errorPair: Pair<Boolean, String>,
    startDestination: String = AppDestinations.NEWS_LIST,
    refresh: () -> Unit
) {

    val navController = rememberNavController()
    val actions = remember(navController) {
        AppActions(navController)
    }

    NavHost(navController = navController, startDestination = startDestination) {
        composable(AppDestinations.NEWS_LIST) {
            NewsList(
                newsResponse = newsResponse,
                selectedNews = actions.selectedNewsItems,
                loading = loading,
                errorPair = errorPair,
                refresh = refresh
            )
        }

        composable(AppDestinations.NEWS_DETAILS) { navBackStackEntry ->

            val arguments = requireNotNull(navBackStackEntry.arguments)
            NewsDetails(
                newsItems = Gson().fromJson(
                    arguments.getString(NEWS_DATA_KEY),
                    NewsItems::class.java
                ), actions.navigateUp
            )
        }

    }
}