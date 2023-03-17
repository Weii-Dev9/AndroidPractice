package com.thoughtworks.androidtrain.helloworld.compose

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun NewTweetScreen(navController: NavController) {
    Box(
        modifier = Modifier.fillMaxWidth().fillMaxHeight(),
        contentAlignment = Alignment.Center
    ) {
        Text(text = "发表页面", fontSize = 20.sp)
    }
}
