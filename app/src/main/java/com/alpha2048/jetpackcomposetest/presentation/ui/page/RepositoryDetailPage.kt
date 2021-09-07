package com.alpha2048.jetpackcomposetest.presentation.ui.page

import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.*
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.viewinterop.AndroidView
import com.alpha2048.jetpackcomposetest.domain.entity.RepositoryEntity

@Composable
fun RepositoryDetailPage(
    repository: RepositoryEntity,
    onBackPress: () -> Unit,
) {
    val scaffoldState = rememberScaffoldState()

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = repository.name,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                    )
                },
                navigationIcon = {
                    IconButton(onClick =  onBackPress ) {
                        Icon(Icons.Default.Close, contentDescription = "")
                    }
                }
            )
        },
    ) {
        AndroidView(
            factory = ::WebView,
            update = { webView ->
                webView.webViewClient = WebViewClient()
                webView.loadUrl(repository.htmlUrl)
            }
        )
    }
}