package com.sf.starwarssearch

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.google.gson.Gson
import com.sf.starwarssearch.domain.model.PeopleItemModel
import com.sf.starwarssearch.ui.detail.DetailScreen
import com.sf.starwarssearch.ui.search.SearchScreen
import java.net.URLDecoder

@Composable
fun NavGraph(navHostController: NavHostController) {
    NavHost(navController = navHostController, startDestination = "search") {
        composable("search") {
            SearchScreen(navHostController)
        }

        composable(route = "detail/{people}", arguments = listOf(navArgument("people") {
            type =
                NavType.StringType
        })) { backStackEntry ->
            val json = backStackEntry.arguments?.getString("people")
            val decodedJson = URLDecoder.decode(json, "UTF-8")
            val people = Gson().fromJson(decodedJson, PeopleItemModel::class.java)
            DetailScreen(people = people, upPress = {
                navHostController.navigateUp()
            })
        }
    }
}