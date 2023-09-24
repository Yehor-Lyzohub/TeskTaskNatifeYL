package com.example.tesktasknatifeyl.navigation

const val DETAILS_ARG_KEY = "url"

sealed class Screen(val route: String) {
    object HomeScreen: Screen("home_screen")
    object DetailsScreen: Screen("details_screen/{$DETAILS_ARG_KEY}") {
        fun passUrl(url: String): String {
            return "details_screen/$url"
        }
    }
}