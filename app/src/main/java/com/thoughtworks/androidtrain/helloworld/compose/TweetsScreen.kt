package com.thoughtworks.androidtrain.helloworld.compose

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalLifecycleOwner
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
import coil.compose.rememberAsyncImagePainter
import com.thoughtworks.androidtrain.helloworld.R
import com.thoughtworks.androidtrain.helloworld.data.model.Tweet

@Composable
fun TweetsScreen() {

    val tweetsViewModel: ComposeViewModel = hiltViewModel()
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

    val tweets = tweetsViewModel.tweets.observeAsState().value

    tweets?.let {
        LazyColumn {
            item { Header() }

            items(tweets) { tweet ->
                TweetItem(tweet)
                divider()
            }

            item { BottomText() }
        }
    }

}

@Composable
private fun TweetItem(tweet: Tweet) {

    val rowPadding = dimensionResource(id = R.dimen.tweet_item_row_padding)
    val spacerWidth = dimensionResource(id = R.dimen.spacer_width_between_avatar_content)
    val spacerHeight = dimensionResource(id = R.dimen.spacer_height_between_nick_content)
    val nickColor = colorResource(id = R.color.light_blue)

    Row(modifier = Modifier.padding(rowPadding)) {
        val painter = rememberAsyncImagePainter(tweet.sender?.avatar)
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
private fun Header() {

    Box {
        HeaderBackground()
        TopAppBar()
    }

}

@Composable
private fun TopAppBar() {

    val elevation = dimensionResource(id = R.dimen.topAppBar_elevation)
    val actionIconSize = dimensionResource(id = R.dimen.action_icon_size)
    val actionIconPaddingTop = dimensionResource(id = R.dimen.action_icon_padding_top)

    TopAppBar(
        title = {},
        backgroundColor = Color.Transparent,
        elevation = elevation,
        navigationIcon = {
            IconButton(onClick = { /*返回*/ }) {
                Icon(
                    Icons.Default.ArrowBack,
                    modifier = Modifier.size(actionIconSize)
                        .padding(top = actionIconPaddingTop),
                    contentDescription = stringResource(R.string.back)
                )
            }
        },
        actions = {
            IconButton(onClick = { /*发表朋友圈*/ }) {
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
private fun BottomText() {

    val bottomHeight = dimensionResource(id = R.dimen.bottom_height)

    Box(
        modifier = Modifier.fillMaxWidth().height(bottomHeight).background(Color.LightGray),
        contentAlignment = Alignment.Center
    ) {
        Text(
            stringResource(R.string.bottom_text),
            fontSize = dimensionResource(R.dimen.font_size_medium).value.sp
        )
    }
}
