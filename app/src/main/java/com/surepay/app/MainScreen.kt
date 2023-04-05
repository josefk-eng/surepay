package com.surepay.app

import android.graphics.Bitmap
import android.util.Log
import android.webkit.WebView
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.google.accompanist.web.*
import com.surepay.app.ui.theme.Purple700
import com.surepay.app.ui.theme.purple500

@Composable
fun MainScreen(modifier: Modifier) {
    var url by remember{ mutableStateOf("https://sims.surepayltd.com/") }
    val state = rememberWebViewState(url = url)
    val navigator = rememberWebViewNavigator()
    var textFieldViewValue by remember(state.content.getCurrentUrl()){
        mutableStateOf(state.content.getCurrentUrl() ?: "")
    }
    
    Column(modifier = modifier) {
        TopAppBar {
            IconButton(onClick = { navigator.navigateBack() }) {
                Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back")
            }
            IconButton(onClick = { navigator.navigateForward() }) {
                Icon(imageVector = Icons.Default.ArrowForward, contentDescription = "Forward")
            }
            Text(text = "SurePay", style = MaterialTheme.typography.h6.copy(Color.White))

            Row(modifier = Modifier.weight(1f), horizontalArrangement = Arrangement.End) {
                    IconButton(onClick = { navigator.reload() }) {
                        Icon(imageVector = Icons.Default.Refresh, contentDescription = "Refresh")
                    }
//                    IconButton(onClick = { url = textFieldViewValue}) {
//                        Icon(imageVector = Icons.Default.Check, contentDescription = "Go")
//                    }
                }

        }
        if (state.errorsForCurrentRequest.isNotEmpty()) {
            Row(modifier = Modifier.padding(all = 12.dp)) {
//            BasicTextField(
//                modifier = Modifier.weight(9f),
//                value = textFieldViewValue,
//                maxLines = 1,
//                onValueChange = { textFieldViewValue = it },
//                readOnly = true,
//                enabled = false
//            )

                Text(
                    text = "An Error Occurred Refresh to try again",
                    maxLines = 1,
                    modifier = Modifier.weight(
                        9f
                    ),
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.caption.copy(color = Color.Red)
                )

                Icon(
                    modifier = Modifier.weight(1f),
                    imageVector = Icons.Default.Warning,
                    contentDescription = "error",
                    tint = Color.Red
                )
            }
        }

        val loadingState = state.loadingState
        if (loadingState is LoadingState.Loading) {
            LinearProgressIndicator(
                progress = loadingState.progress,
                modifier = Modifier.fillMaxWidth(),
                backgroundColor = purple500
            )
        }
        val client = remember{
            object: AccompanistWebViewClient(){
                override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                    super.onPageStarted(view, url, favicon)
                    Log.d("Accompanist Webview", "Page Started Loading For $url")
                }
            }
        }

        WebView(
            state = state,
            modifier = Modifier.weight(1f),
            navigator = navigator,
            onCreated = {
                it.settings.javaScriptEnabled = true
            },
            client = client
        )
    }
}