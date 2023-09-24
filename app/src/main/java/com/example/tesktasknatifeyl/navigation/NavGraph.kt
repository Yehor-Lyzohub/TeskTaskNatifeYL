package com.example.tesktasknatifeyl.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.tesktasknatifeyl.ui.DetailsScreen
import com.example.tesktasknatifeyl.ui.HomeScreen

@Composable
fun SetupNetGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screen.HomeScreen.route
    ) {
        composable(route = Screen.HomeScreen.route) {
            HomeScreen(navController)
        }
        composable(
            route = Screen.DetailsScreen.route,
            arguments = listOf(navArgument(DETAILS_ARG_KEY) {
                type = NavType.StringType
            })
        ) {
            backStackEntry ->
                DetailsScreen(
                    backStackEntry.arguments?.getString(DETAILS_ARG_KEY).toString()
                )
        }
    }
}