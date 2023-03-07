package com.thoughtworks.androidtrain.helloworld.compose

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import coil.compose.rememberAsyncImagePainter
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
            items(it) { tweet ->
                TweetItem(tweet)
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
    val flag = remember { mutableStateOf(false) }
    Row(modifier = Modifier.padding(all = 8.dp)) {
        val painter = rememberAsyncImagePainter(tweet.sender?.avatar)
        //val painter = painterResource(R.mipmap.avatar)
        Avatar(painter)

        Spacer(modifier = Modifier.width(8.dp))

        Column {
            tweet.sender?.nick?.let {
                Text(
                    text = it,
                    color = MaterialTheme.colors.secondaryVariant,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(4.dp))

            Box(Modifier.fillMaxWidth().clickable(onClick = {
                flag.value = true
            })) {
                Text(
                    text = tweet.content, style = MaterialTheme.typography.body2,
                )
            }
            if (flag.value) {
                Comment()
            }
        }
    }
}

@Composable
private fun Avatar(painter: Painter) {
    val showDialog = remember { mutableStateOf(false) }
    Image(
        painter = painter,
        contentDescription = "avatar",
        modifier = Modifier.size(60.dp).clip(CircleShape).clickable { showDialog.value = true },
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
    Dialog(onDismissRequest = onDismiss) {
        Box(
            Modifier.fillMaxWidth()
                .height(300.dp)
                .padding(20.dp)
                .background(Color.White)
        ) {
            Image(
                painter = painter,
                contentDescription = "BigAvatar",
                modifier = Modifier.size(200.dp)
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

@Preview
@Composable
private fun PreviewTest() {
    TweetsScreen()
}

@Composable
private fun BottomText() {
    Box(
        modifier = Modifier.fillMaxSize(), Alignment.BottomStart
    ) {
        Text(
            "到底了",
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth().padding(top = 10.dp).height(60.dp)
                .background(Color.LightGray)
        )
    }
}
