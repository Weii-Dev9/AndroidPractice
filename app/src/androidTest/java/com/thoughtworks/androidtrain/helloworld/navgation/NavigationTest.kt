package com.thoughtworks.androidtrain.helloworld.navgation

import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import com.thoughtworks.androidtrain.helloworld.compose.ComposeViewModel
import com.thoughtworks.androidtrain.helloworld.navigation.NavGraph
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject
import javax.inject.Named

private const val avatar = "avatar"
private const val publicTweet = "发表朋友圈"
private const val newTweetScreen = "new_tweet_screen"

@HiltAndroidTest
class NavigationTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @get:Rule
    var composeTestRule = createComposeRule()
    lateinit var navController: TestNavHostController

    @Inject
    @Named("test_vm")
    lateinit var vm: ComposeViewModel

    @Before
    fun setUp() {
        hiltRule.inject()
        composeTestRule.setContent {
            navController = TestNavHostController(LocalContext.current)
            navController.navigatorProvider.addNavigator(ComposeNavigator())
            NavGraph(navController, vm)
        }
    }

    @Test
    fun testNavHostVerifyStartDestination() {
        composeTestRule
            .onNodeWithContentDescription(avatar)
            .assertIsDisplayed()
    }

    @Test
    fun testNavHostClickPublicTweetNavigateToNewTweet() {
        composeTestRule.onNodeWithContentDescription(publicTweet)
            .performScrollTo()
            .performClick()
        val route = navController.currentBackStackEntry?.destination?.route
        assertEquals(route, newTweetScreen)
    }

}
