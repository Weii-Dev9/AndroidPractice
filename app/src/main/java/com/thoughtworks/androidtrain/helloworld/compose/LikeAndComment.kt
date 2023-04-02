package com.thoughtworks.androidtrain.helloworld.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties
import com.thoughtworks.androidtrain.helloworld.R
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun LikeAndComment(selectedItemIndex: Int, lazyListState: LazyListState) {

    var expanded by remember { mutableStateOf(false) }
    var isLiked by remember { mutableStateOf(false) }
    var isDialogShown by remember { mutableStateOf(false) }
    var comment by remember { mutableStateOf("") }
    val focusRequester = remember { FocusRequester() }
    val keyboardController = LocalSoftwareKeyboardController.current

    val coroutineScope = rememberCoroutineScope()


    IconButton(
        onClick = { expanded = true },
        modifier = Modifier.size(24.dp)
    ) {
        Icon(
            Icons.Default.MoreHoriz,
            contentDescription = stringResource(R.string.menu)
        )

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            DropdownMenuItem(onClick = { isLiked = !isLiked }) {
                Icon(
                    imageVector = if (isLiked) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                    contentDescription = stringResource(R.string.like),
                    tint = if (isLiked) Color.Red else Color.Unspecified
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = if (isLiked) stringResource(R.string.cancel) else stringResource(
                        R.string.like_zh
                    )
                )
            }

            DropdownMenuItem(onClick = {
                isDialogShown = true
                coroutineScope.launch {
                    lazyListState.scrollToItem(
                        index = selectedItemIndex + 1,
                    )
                }
            }) {
                Icon(
                    Icons.Default.ChatBubbleOutline,
                    contentDescription = stringResource(R.string.like)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(text = stringResource(R.string.comment))
            }

        }
    }

    if (isDialogShown) {
        expanded = false
        LaunchedEffect(Unit) {
            delay(200)
            focusRequester.requestFocus()
            keyboardController?.show()
        }
        Popup(
            onDismissRequest = {
                keyboardController?.hide()
                isDialogShown = false
            },
            content = {
                Box(
                    modifier = Modifier
                        .clickable(
                            onClick = { isDialogShown = false },
                            indication = null,
                            interactionSource = remember {
                                MutableInteractionSource()
                            })
                        .fillMaxSize()
                        .background(Color.Transparent),
                    contentAlignment = Alignment.BottomCenter
                ) {
                    OutlinedTextField(
                        value = comment,
                        onValueChange = { comment = it },
                        modifier = Modifier.fillMaxWidth()
                            .focusRequester(focusRequester).background(Color.White),
                        colors = TextFieldDefaults.outlinedTextFieldColors(focusedBorderColor = Color.LightGray),
                        keyboardOptions = KeyboardOptions(//软键盘操作
                            keyboardType = KeyboardType.Text,//键盘类型
                            imeAction = ImeAction.Default
                        ),
                        maxLines = 4,
                        trailingIcon = {
                            IconButton(onClick = { /*TODO:发送评论逻辑*/ }) {
                                Icon(
                                    Icons.Filled.Send,
                                    contentDescription = stringResource(R.string.send)
                                )
                            }
                        },
                        textStyle = MaterialTheme.typography.subtitle1
                    )
                }
            },
            properties = PopupProperties(
                focusable = true,
                dismissOnBackPress = true,
                dismissOnClickOutside = true
            )
        )
    }
}
