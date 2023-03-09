package com.thoughtworks.androidtrain.helloworld.compose

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
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
            //顶部 item{}
            //内容
            items(tweets) { tweet ->
                TweetItem(tweet)
                if (tweet != tweets.last()) {
                    divider()
                }
            }
            //底部
            item {
                BottomText()
            }
        }
    }
}

@Composable
private fun TweetItem(tweet: Tweet) {

    val value16dp = dimensionResource(id = R.dimen.dp_16)
    val value8dp = dimensionResource(id = R.dimen.dp_8)
    val nickColor = colorResource(id = R.color.light_blue)

    Row(modifier = Modifier.padding(value16dp)) {
        val painter = rememberAsyncImagePainter(tweet.sender?.avatar)
        Avatar(painter)

        Spacer(modifier = Modifier.width(value16dp))

        Column(horizontalAlignment = Alignment.Start) {
            tweet.sender?.nick?.let {
                Text(
                    text = it,
                    fontWeight = FontWeight.Bold,
                    color = nickColor
                )
            }

            Spacer(modifier = Modifier.height(value8dp))

            Text(
                text = tweet.content,
                style = MaterialTheme.typography.body1,
            )

            Spacer(modifier = Modifier.height(value16dp))

        }
    }
}

@Composable
private fun Avatar(painter: Painter) {

    val showDialog = remember { mutableStateOf(false) }
    val value60dp = dimensionResource(id = R.dimen.dp_60)

    Image(
        painter = painter,
        contentDescription = "avatar",
        modifier = Modifier.size(value60dp).clip(CircleShape).clickable { showDialog.value = true },
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

    val value300dp = dimensionResource(id = R.dimen.dp_300)
    val value20dp = dimensionResource(id = R.dimen.dp_20)
    val value200dp = dimensionResource(id = R.dimen.dp_200)

    Dialog(onDismissRequest = onDismiss) {
        Box(
            Modifier.fillMaxWidth()
                .height(value300dp)
                .padding(value20dp)
                .background(Color.White)
        ) {
            Image(
                painter = painter,
                contentDescription = "BigAvatar",
                modifier = Modifier.size(value200dp)
                    .clip(CircleShape)
                    .align(Alignment.Center),
                contentScale = ContentScale.Crop
            )
        }
    }
}

@Composable
private fun Comment() {
    val textValue = remember { mutableStateOf("") }
    val input = remember { mutableStateOf(true) }

    Row {
        Box(
            Modifier.weight(1f)
        ) {
            TextField(value = textValue.value, onValueChange = {
                textValue.value = it
            }, enabled = input.value)
        }
        Box(
            Modifier.weight(1f),
            contentAlignment = Alignment.Center
        ) {
            Button(onClick = { input.value = false }) {
                Text(text = "save")
            }
        }
        Box(
            Modifier.weight(1f),
            contentAlignment = Alignment.Center
        ) {
            Button(onClick = {
                textValue.value = ""
                input.value = true
            }) {
                Text(text = "cancel")
            }
        }


    }

}

@Composable
private fun divider() {

    val value05dp = dimensionResource(id = R.dimen.dp_0_5)

    Divider(
        color = Color.LightGray,
        thickness = value05dp,
        modifier = Modifier.fillMaxWidth()
    )
}

@Composable
private fun BottomText() {

    val value10dp = dimensionResource(id = R.dimen.dp_10)
    val value60dp = dimensionResource(id = R.dimen.dp_60)

    Box(
        modifier = Modifier.fillMaxSize(), Alignment.BottomStart
    ) {
        Text(
            "到底了",
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth().padding(top = value10dp).height(value60dp)
                .background(Color.LightGray)
        )
    }
}
