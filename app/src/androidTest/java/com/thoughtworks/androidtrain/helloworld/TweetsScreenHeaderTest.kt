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
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

private const val background = "背景图片"
private const val personalNick = "Weii"
private const val avatar = "avatar"
private const val back = "返回"
private const val publicTweet = "发表朋友圈"

@RunWith(AndroidJUnit4::class)
class TweetsScreenHeaderTest {


    @get:Rule
    var composeTestRule = createComposeRule()

    @Test
    fun profileTest() {
        composeTestRule.setContent {
            Image(
                painter = painterResource(id = R.mipmap.background),
                contentDescription = background,
                contentScale = ContentScale.FillWidth,
                modifier = Modifier
                    .fillMaxSize()
            )
            Text(
                text = personalNick,
                fontWeight = FontWeight.Bold,
                color = Color.White,
            )
            Image(
                painter = painterResource(id = R.mipmap.personal),
                contentDescription = avatar,
                contentScale = ContentScale.Crop,
            )
        }
        composeTestRule.onNodeWithContentDescription(background).assertIsDisplayed()
        composeTestRule.onNodeWithContentDescription(avatar).assertIsDisplayed()
        composeTestRule.onNode(hasText(personalNick)).assertIsDisplayed()
    }

    @Test
    fun topBarTest() {
        composeTestRule.setContent {
            TopAppBar(
                title = {},
                backgroundColor = Color.Transparent,
                elevation = 0.dp,
                navigationIcon = {
                    IconButton(onClick = { /*TODO:返回*/ }) {
                        Icon(
                            Icons.Default.ArrowBack,
                            contentDescription = back
                        )
                    }
                },
                actions = {
                    IconButton(onClick = { /*TODO:发表朋友圈*/ }) {
                        Icon(
                            painter = painterResource(id = R.mipmap.camera),
                            contentDescription = publicTweet
                        )
                    }
                }
            )
        }
        composeTestRule.onNodeWithContentDescription(back).assertIsDisplayed()
        composeTestRule.onNodeWithContentDescription(back).performClick()
        composeTestRule.onNodeWithContentDescription(publicTweet).assertIsDisplayed()
        composeTestRule.onNodeWithContentDescription(publicTweet).performClick()
    }

}
