package com.thoughtworks.androidtrain.helloworld.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.thoughtworks.androidtrain.helloworld.compose.ComposeViewModel
import com.thoughtworks.androidtrain.helloworld.compose.NewTweetScreen
import com.thoughtworks.androidtrain.helloworld.compose.TweetsScreen

@Composable
fun NavGraph(
    navHostController: NavHostController = rememberNavController(),
    tweetsViewModel: ComposeViewModel = hiltViewModel()
) {
    NavHost(navController = navHostController, startDestination = Screen.TweetsScreen.route) {
        composable(route = Screen.TweetsScreen.route) {
            TweetsScreen(navHostController, tweetsViewModel)
        }
        composable(route = Screen.NewTweetScreen.route) {
            NewTweetScreen(navHostController, tweetsViewModel)
        }
    }
}
