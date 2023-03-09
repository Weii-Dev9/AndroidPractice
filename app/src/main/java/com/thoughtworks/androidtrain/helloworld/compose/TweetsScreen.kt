package com.thoughtworks.androidtrain.helloworld.compose

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
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

            items(tweets) { tweet ->
                TweetItem(tweet)
                if (tweet != tweets.last()) {
                    divider()
                }
            }

            item {
                BottomText()
            }
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

    val thickness = dimensionResource(id = R.dimen.divider_line_thickness)

    Divider(
        color = Color.LightGray,
        thickness = thickness,
        modifier = Modifier.fillMaxWidth()
    )
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
