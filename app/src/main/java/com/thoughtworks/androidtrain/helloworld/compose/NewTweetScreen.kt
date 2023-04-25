package com.thoughtworks.androidtrain.helloworld.compose

import android.annotation.SuppressLint
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.thoughtworks.androidtrain.helloworld.R
import com.thoughtworks.androidtrain.helloworld.navigation.Screen

@OptIn(ExperimentalComposeUiApi::class)
@SuppressLint("RememberReturnType")
@Composable
fun NewTweetScreen(navController: NavController, tweetsViewModel: ComposeViewModel) {

    var postText by remember { mutableStateOf("") }
    val flag = postText.isNotBlank()


    val focusRequester = remember { FocusRequester() }
    val softKeyboard = LocalSoftwareKeyboardController.current
    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
        softKeyboard?.show()
    }


    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        TopAppBar(navController, tweetsViewModel, flag)

        OutlinedTextField(
            value = postText,
            onValueChange = {
                postText = it
                tweetsViewModel.tweet.value.content = postText
            },
            textStyle = TextStyle(fontSize = 16.sp),
            placeholder = { Text(stringResource(R.string.NowThinking)) },
            maxLines = 10,
            modifier = Modifier.fillMaxWidth()
                .padding(dimensionResource(R.dimen.outlined_text_field_padding))
                .focusRequester(focusRequester),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color.Transparent,
                unfocusedBorderColor = Color.Transparent,
            )
        )
    }

}

@Composable
fun TopAppBar(navController: NavController, tweetsViewModel: ComposeViewModel, flag: Boolean) {

    val color = Color(android.graphics.Color.parseColor(stringResource(R.string.LightGrayWhite)))
    var showDialog by remember { mutableStateOf(false) }

    BackHandler {
        showDialog = true
    }

    TopAppBar(
        title = {
            Box(
                modifier = Modifier.fillMaxWidth()
                    .padding(top = dimensionResource(R.dimen.action_icon_padding_top)),
                contentAlignment = Alignment.Center
            ) { Text(text = stringResource(R.string.public_words)) }
        },
        backgroundColor = color,
        elevation = dimensionResource(id = R.dimen.topAppBar_elevation),
        navigationIcon = {
            IconButton(onClick = { showDialog = true }) {

                Icon(
                    Icons.Default.ArrowBack,
                    modifier = Modifier.size(dimensionResource(id = R.dimen.action_icon_size))
                        .padding(top = dimensionResource(R.dimen.action_icon_padding_top)),
                    contentDescription = stringResource(R.string.back)
                )
            }
        },
        actions = {
            Button(
                onClick = {
                    tweetsViewModel.insertData(tweetsViewModel.tweet.value)
                    tweetsViewModel.fetchTweets()
                    navController.navigate(Screen.TweetsScreen.route)
                },
                colors = ButtonDefaults.buttonColors(Color.Green),
                enabled = flag,
                modifier = Modifier.padding(top = dimensionResource(R.dimen.action_icon_padding_top)),
            ) {
                Text(text = stringResource(R.string.pblic))
            }
        }
    )
    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = {
                Text(
                    stringResource(R.string.exit_editor),
                    fontWeight = FontWeight.Bold,
                    fontSize = dimensionResource(R.dimen.font_size_medium).value.sp
                )
            },
            text = { Text(stringResource(R.string.exit_and_no_reserve)) },
            confirmButton = {
                Button(
                    onClick = {
                        showDialog = false
                        navController.navigate(Screen.TweetsScreen.route)
                    },
                ) {
                    Text(stringResource(R.string.exit))
                }
            },
            dismissButton = {
                Alignment.End
                Button(onClick = { showDialog = false }) {
                    Text(stringResource(android.R.string.cancel))
                }
            }
        )
    }
}

