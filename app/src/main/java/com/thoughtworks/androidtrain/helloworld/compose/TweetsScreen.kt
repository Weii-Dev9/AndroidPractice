package com.thoughtworks.androidtrain.helloworld.compose

import android.app.Activity
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.*
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.thoughtworks.androidtrain.helloworld.R
import com.thoughtworks.androidtrain.helloworld.data.model.Tweet
import com.thoughtworks.androidtrain.helloworld.navigation.Screen
import com.thoughtworks.androidtrain.helloworld.utils.SoftKeyBoardListener

@OptIn(ExperimentalLayoutApi::class)
@ExperimentalComposeUiApi
@Composable
fun TweetsScreen(
    navController: NavController,
    tweetsViewModel: ComposeViewModel = hiltViewModel()
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
    val tweets = tweetsViewModel.tweets.value.reversed()
    val lazyListState = rememberLazyListState()
    var selectedItemIndex by remember { mutableStateOf(-1) }

    var keyBoardHeight by remember { mutableStateOf(0) }
    SoftKeyBoardListener.setListener(LocalContext.current as Activity, object :
        SoftKeyBoardListener.OnSoftKeyBoardChangeListener {
        override fun keyBoardShow(height: Int) {
            keyBoardHeight = height
        }

        override fun keyBoardHide(height: Int) {
            keyBoardHeight = height
        }
    })


    Column(Modifier.statusBarsPadding().navigationBarsPadding()) {
        LazyColumn(
            Modifier.weight(1f).imeNestedScroll(),
            state = lazyListState,
        ) {
            item { Header(navController) }

            itemsIndexed(tweets) { index, tweet ->
                selectedItemIndex = index
                TweetItem(tweet, selectedItemIndex, lazyListState)
                if (tweets.lastIndex != index) {
                    divider()
                }
            }

            item {
                Space(keyBoardHeight)
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
private fun TweetItem(tweet: Tweet, selectedItemIndex: Int, lazyListState: LazyListState) {

    val rowPadding = dimensionResource(id = R.dimen.tweet_item_row_padding)
    val spacerWidth = dimensionResource(id = R.dimen.spacer_width_between_avatar_content)
    val spacerHeight = dimensionResource(id = R.dimen.spacer_height_between_nick_content)
    val nickColor = colorResource(id = R.color.light_blue)

    Row(
        modifier = Modifier.padding(rowPadding)
    ) {
        val painter: Painter =
            if (tweet.sender?.avatar == stringResource(R.string.own_avatar)) {
                painterResource(id = R.mipmap.personal)
            } else {
                rememberAsyncImagePainter(tweet.sender?.avatar)
            }
        Avatar(painter)

        Spacer(modifier = Modifier.width(spacerWidth))

        Column(horizontalAlignment = Alignment.Start) {
            tweet.sender?.nick?.let {
                Text(
                    text = it,
                    fontWeight = FontWeight.Bold,
                    color = nickColor
                )
            }

            Spacer(modifier = Modifier.height(spacerHeight))

            Text(
                text = tweet.content,
                style = MaterialTheme.typography.body1,
            )

            Box(
                Modifier.fillMaxWidth(),
                contentAlignment = Alignment.BottomEnd
            ) {
                LikeAndComment(selectedItemIndex, lazyListState)
            }
        }
    }
}

@Composable
private fun Avatar(painter: Painter) {

    val showDialog = remember { mutableStateOf(false) }
    val avatarSize = dimensionResource(id = R.dimen.avatar_size)

    Image(
        painter = painter,
        contentDescription = stringResource(R.string.avatar),
        modifier = Modifier.size(avatarSize).clip(CircleShape)
            .clickable { showDialog.value = true },
        contentScale = ContentScale.Crop
    )
    if (showDialog.value) {
        BigAvatar(painter) {
            showDialog.value = false
        }
    }
}

@Composable
private fun BigAvatar(painter: Painter, onDismiss: () -> Unit) {

    val bigAvatarSize = dimensionResource(id = R.dimen.big_avatar_size)

    Dialog(onDismissRequest = onDismiss) {
        Box(
            Modifier.fillMaxWidth()
        ) {
            Image(
                painter = painter,
                contentDescription = stringResource(R.string.BigAvatar),
                modifier = Modifier.size(bigAvatarSize)
                    .clip(CircleShape)
                    .align(Alignment.Center),
                contentScale = ContentScale.Crop
            )
        }
    }
}

@Composable
private fun divider() {
    Divider(
        color = Color.LightGray,
        modifier = Modifier.fillMaxWidth()
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

    TopAppBar(
        title = {},
        backgroundColor = Color.Transparent,
        elevation = elevation,
        navigationIcon = {
            IconButton(onClick = { /*TODO:place holder*/ }) {
                Icon(
                    Icons.Default.ArrowBack,
                    modifier = Modifier.size(actionIconSize)
                        .padding(top = actionIconPaddingTop),
                    contentDescription = stringResource(R.string.back)
                )
            }
        },
        actions = {
            IconButton(onClick = { navController.navigate(Screen.NewTweetScreen.route) }) {
                Icon(
                    painter = painterResource(id = R.mipmap.camera),
                    modifier = Modifier.size(actionIconSize)
                        .padding(top = actionIconPaddingTop),
                    contentDescription = stringResource(R.string.public_friends_circle)
                )
            }
        }
    )
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
        modifier = Modifier
            .height(backgroundHeight)
            .fillMaxWidth()
    ) {
        Image(
            painter = painterResource(id = R.mipmap.background),
            contentDescription = stringResource(R.string.background),
            contentScale = ContentScale.FillWidth,
            modifier = Modifier
                .fillMaxSize()
        )
        Text(
            text = stringResource(R.string.personal_nick),
            fontSize = personalNicknameSize.value.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(end = personalNicknamePaddingEnd)
        )
        Image(
            painter = painterResource(id = R.mipmap.personal),
            contentDescription = stringResource(R.string.avatar),
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(personalAvatarSize)
                .padding(personalAvatarPadding)
                .align(Alignment.BottomEnd)
                .offset(y = personalAvatarOffSetY)
                .clip(CircleShape)
        )
    }

}

@Composable
private fun BottomDivider() {
    Row(
        modifier = Modifier.fillMaxWidth().padding(bottom = 30.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Divider(
            modifier = Modifier.weight(1f).padding(start = 50.dp),
            color = Color.LightGray,
        )
        Text(
            stringResource(R.string.bottom_text),
            fontSize = dimensionResource(R.dimen.font_size_medium).value.sp,
            modifier = Modifier.padding(horizontal = 8.dp)
        )
        Divider(
            modifier = Modifier.weight(1f).padding(end = 50.dp),
            color = Color.LightGray,
        )
    }
}
