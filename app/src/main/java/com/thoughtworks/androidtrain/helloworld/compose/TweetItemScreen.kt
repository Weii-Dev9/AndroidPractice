package com.thoughtworks.androidtrain.helloworld.compose

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.window.Dialog
import coil.compose.rememberAsyncImagePainter
import com.thoughtworks.androidtrain.helloworld.R
import com.thoughtworks.androidtrain.helloworld.data.model.Tweet

@Composable
fun TweetItem(
    tweet: Tweet,
    index: Int,
    onClickAction: (index: Int, rowHeight: Int) -> Unit
) {

    val rowPadding = dimensionResource(id = R.dimen.tweet_item_row_padding)
    val spacerWidth = dimensionResource(id = R.dimen.spacer_width_between_avatar_content)
    val spacerHeight = dimensionResource(id = R.dimen.spacer_height_between_nick_content)
    val nickColor = colorResource(id = R.color.light_blue)
    var rowHeight by remember { mutableStateOf(0) }
    Row(
        modifier = Modifier.padding(rowPadding)
            .onGloballyPositioned { coordinates -> rowHeight = coordinates.size.height }
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
                LikeAndComment(onClickAction = {
                    onClickAction(index, rowHeight)
                })
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
