package com.thoughtworks.androidtrain.helloworld.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChatBubbleOutline
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.MoreHoriz
import androidx.compose.material.icons.filled.Send
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties
import com.thoughtworks.androidtrain.helloworld.R
import kotlinx.coroutines.delay


@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun LikeAndComment(
    onCommentClick: () -> Unit,
) {

    var expanded by remember { mutableStateOf(false) }
    var isLiked by remember { mutableStateOf(false) }
    var isDialogShown by remember { mutableStateOf(false) }
    var comment by remember { mutableStateOf("") }
    val focusRequester = remember { FocusRequester() }
    val keyboardController = LocalSoftwareKeyboardController.current


    IconButton(
        onClick = { expanded = true },
        modifier = Modifier.size(dimensionResource(R.dimen.like_commend_icon_size))
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
                Spacer(modifier = Modifier.width(dimensionResource(R.dimen.like_commend_dropdownMenu_spacer_width)))
                Text(
                    text = if (isLiked) stringResource(android.R.string.cancel) else stringResource(
                        R.string.like_zh
                    )
                )
            }
            DropdownMenuItem(onClick = {
                isDialogShown = true
                onCommentClick()
            }) {
                Icon(
                    Icons.Default.ChatBubbleOutline,
                    contentDescription = stringResource(R.string.like)
                )
                Spacer(modifier = Modifier.width(dimensionResource(R.dimen.like_commend_dropdownMenu_spacer_width)))
                Text(text = stringResource(R.string.comment))
            }

        }
    }

    if (isDialogShown) {
        expanded = false
        LaunchedEffect(Unit) {
            /*TODO: 解决焦点获取和Popup()出现的时机问题*/
            delay(10)
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
