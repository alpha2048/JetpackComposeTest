package com.alpha2048.jetpackcomposetest.ui_component.component.organisms

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ErrorLayout (
    e: Exception,
    onClickRetry: () -> Unit,
){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .wrapContentSize(Alignment.Center),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(text = "エラーが発生しました",modifier = Modifier.padding(8.dp))
        Text(text = "エラー内容: " + e.message ,modifier = Modifier.padding(start = 8.dp, end = 8.dp, bottom = 8.dp))
        Button(onClick = onClickRetry) {
            Text(text = "再実行")
        }
    }
}