package com.thoughtworks.androidtrain.helloworld.compose

import android.app.Activity
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imeNestedScroll
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.NavController
import com.thoughtworks.androidtrain.helloworld.R
import com.thoughtworks.androidtrain.helloworld.navigation.Screen
import com.thoughtworks.androidtrain.helloworld.utils.SoftKeyBoardListener
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalLayoutApi::class)
@ExperimentalComposeUiApi
@Composable
fun TweetsScreen(
    navController: NavController, tweetsViewModel: ComposeViewModel = hiltViewModel()
) {
    val lifeCycleOwner: LifecycleOwner = LocalLifecycleOwner.current
    DisposableEffect(lifeCycleOwner) {
        val observer = (LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_CREATE) {
                tweetsViewModel.fetchTweets()
            }
        })
        lifeCycleOwner.lifecycle.addObserver(observer)

        onDispose {
            lifeCycleOwner.lifecycle.removeObserver(observer)
        }
    }

    LaunchedEffect(Unit) {
        tweetsViewModel.checkIsCurrentUser()
    }

    val tweets = tweetsViewModel.tweets.value.reversed()
    val lazyListState = rememberLazyListState()
    var selectedItemIndex by remember { mutableStateOf(-1) }

    var keyBoardHeight by remember { mutableStateOf(0) }
    SoftKeyBoardListener.setListener(
        LocalContext.current as Activity,
        object : SoftKeyBoardListener.OnSoftKeyBoardChangeListener {
            override fun keyBoardShow(height: Int) {
                keyBoardHeight = height
            }

            override fun keyBoardHide(height: Int) {
                keyBoardHeight = 0
            }
        })

    val coroutineScope = rememberCoroutineScope()
    var itemHeight by remember { mutableStateOf(0) }
    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.dp
    val paddingValues =
        dimensionResource(id = R.dimen.tweet_item_row_padding).value * 2 + dimensionResource(R.dimen.bottom_divider_bottom_padding).value
    val screenHeightIntValue =
        ((screenHeight.value - paddingValues) * LocalDensity.current.density + 0.5f).toInt()
    LaunchedEffect(keyBoardHeight) {
        if (keyBoardHeight != 0) {
            coroutineScope.launch {
                delay(20)
                lazyListState.scrollToItem(
                    selectedItemIndex + 1, -(screenHeightIntValue - keyBoardHeight - itemHeight)
                )
            }
        }
    }

    Column(Modifier.statusBarsPadding().navigationBarsPadding().imeNestedScroll()) {
        LazyColumn(
            Modifier.imeNestedScroll(),
            state = lazyListState,
        ) {
            item { Header(navController) }

            itemsIndexed(tweets) { index, tweet ->
                TweetItem(tweet, index) { selectedIndex, rowHeight ->
                    selectedItemIndex = selectedIndex
                    itemHeight = rowHeight

                }
                if (tweets.lastIndex != index) {
                    divider()
                }
            }
            item {
                if (keyBoardHeight != 0) {
                    Space(keyBoardHeight)
                }
            }
            item {
                BottomDivider()
            }
        }
    }
}

@Composable
private fun Space(keyBoardHeight: Int) {
    val density = LocalDensity.current
    Spacer(Modifier.height(with(density) { keyBoardHeight.toDp() }))
}


@Composable
private fun divider() {
    Divider(
        color = Color.LightGray, modifier = Modifier.fillMaxWidth()
    )
}

@Composable
private fun Header(navController: NavController) {

    Box {
        HeaderBackground()
        TopAppBar(navController)
    }

}

@Composable
private fun TopAppBar(navController: NavController) {

    val elevation = dimensionResource(id = R.dimen.topAppBar_elevation)
    val actionIconSize = dimensionResource(id = R.dimen.action_icon_size)
    val actionIconPaddingTop = dimensionResource(id = R.dimen.action_icon_padding_top)

    TopAppBar(title = {},
        backgroundColor = Color.Transparent,
        elevation = elevation,
        navigationIcon = {
            IconButton(onClick = { /*TODO:place holder*/ }) {
                Icon(
                    Icons.Default.ArrowBack,
                    modifier = Modifier.size(actionIconSize).padding(top = actionIconPaddingTop),
                    contentDescription = stringResource(R.string.back)
                )
            }
        },
        actions = {
            IconButton(onClick = {
                navController.navigate(Screen.NewTweetScreen.route)
            }) {
                Icon(
                    painter = painterResource(id = R.mipmap.camera),
                    modifier = Modifier.size(actionIconSize).padding(top = actionIconPaddingTop),
                    contentDescription = stringResource(R.string.public_friends_circle)
                )
            }
        })
}

@Composable
private fun HeaderBackground() {

    val backgroundHeight = dimensionResource(id = R.dimen.header_background_height)
    val personalNicknameSize = dimensionResource(id = R.dimen.personal_nick_size)
    val personalNicknamePaddingEnd = dimensionResource(id = R.dimen.personal_nick_padding_end)
    val personalAvatarSize = dimensionResource(id = R.dimen.personal_avatar)
    val personalAvatarPadding = dimensionResource(id = R.dimen.personal_avatar_padding)
    val personalAvatarOffSetY = dimensionResource(id = R.dimen.personal_avatar_offset_y)

    Box(
        modifier = Modifier.height(backgroundHeight).fillMaxWidth()
    ) {
        Image(
            painter = painterResource(id = R.mipmap.background),
            contentDescription = stringResource(R.string.background),
            contentScale = ContentScale.FillWidth,
            modifier = Modifier.fillMaxSize()
        )
        Text(
            text = stringResource(R.string.personal_nick),
            fontSize = personalNicknameSize.value.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            modifier = Modifier.align(Alignment.BottomEnd).padding(end = personalNicknamePaddingEnd)
        )
        Image(
            painter = painterResource(id = R.mipmap.personal),
            contentDescription = stringResource(R.string.avatar),
            contentScale = ContentScale.Crop,
            modifier = Modifier.size(personalAvatarSize).padding(personalAvatarPadding)
                .align(Alignment.BottomEnd).offset(y = personalAvatarOffSetY).clip(CircleShape)
        )
    }

}

@Composable
private fun BottomDivider() {
    Row(
        modifier = Modifier.fillMaxWidth()
            .padding(bottom = dimensionResource(R.dimen.bottom_divider_bottom_padding)),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Divider(
            modifier = Modifier.weight(1f)
                .padding(start = dimensionResource(R.dimen.bottom_divider_item_divider_padding)),
            color = Color.LightGray,
        )
        Text(
            stringResource(R.string.bottom_text),
            fontSize = dimensionResource(R.dimen.font_size_medium).value.sp,
            modifier = Modifier.padding(horizontal = dimensionResource(R.dimen.bottom_divider_text_horizontal))
        )
        Divider(
            modifier = Modifier.weight(1f)
                .padding(end = dimensionResource(R.dimen.bottom_divider_item_divider_padding)),
            color = Color.LightGray,
        )
    }
}
