package com.thoughtworks.androidtrain.helloworld.navigation

sealed class Screen(val route: String) {
    object TweetsScreen : Screen("tweets_screen")
    object NewTweetScreen : Screen("new_tweet_screen")
}
