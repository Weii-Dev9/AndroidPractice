package com.thoughtworks.androidtrain.helloworld

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.performClick
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ComposeTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun profileTest() {
        composeTestRule.setContent {
            Image(
                painter = painterResource(id = R.mipmap.background),
                contentDescription = "背景图片",
                contentScale = ContentScale.FillWidth,
                modifier = Modifier
                    .fillMaxSize()
            )
            Text(
                text = "Weii",
                fontWeight = FontWeight.Bold,
                color = Color.White,
            )
            Image(
                painter = painterResource(id = R.mipmap.personal),
                contentDescription = "avatar",
                contentScale = ContentScale.Crop,
            )
        }
        composeTestRule.onNodeWithContentDescription("背景图片").assertIsDisplayed()
        composeTestRule.onNodeWithContentDescription("avatar").assertIsDisplayed()
        composeTestRule.onNode(hasText("Weii")).assertIsDisplayed()
    }

    @Test
    fun topBarTest() {
        composeTestRule.setContent {
            TopAppBar(
                title = {},
                backgroundColor = Color.Transparent,
                elevation = 0.dp,
                navigationIcon = {
                    IconButton(onClick = { /*返回*/ }) {
                        Icon(
                            Icons.Default.ArrowBack,
                            contentDescription = "返回"
                        )
                    }
                },
                actions = {
                    IconButton(onClick = { /*发表朋友圈*/ }) {
                        Icon(
                            painter = painterResource(id = R.mipmap.camera),
                            contentDescription = "发表朋友圈"
                        )
                    }
                }
            )
        }
        composeTestRule.onNodeWithContentDescription("返回").assertIsDisplayed()
        composeTestRule.onNodeWithContentDescription("返回").performClick()
        composeTestRule.onNodeWithContentDescription("发表朋友圈").assertIsDisplayed()
        composeTestRule.onNodeWithContentDescription("发表朋友圈").performClick()
    }

}
