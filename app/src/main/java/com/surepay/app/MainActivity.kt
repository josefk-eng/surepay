package com.surepay.app

import android.content.Context
import android.os.Bundle
import android.webkit.WebView
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.Transparent
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.view.WindowCompat
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.surepay.app.ui.theme.SurePayTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            SurePayTheme {
                // A surface container using the 'background' color from the theme
                val context = LocalContext.current
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
//                    val systemUiController = rememberSystemUiController()
//                    systemUiController.setStatusBarColor(color = Transparent)
//                    systemUiController.setNavigationBarColor(color = Transparent)
                    MainScreen(modifier = Modifier.systemBarsPadding())
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    SurePayTheme {
        Greeting("Android")
    }
}


@Composable
fun LoadPage(context: Context) {
    val url = "https://sims.surepayltd.com/"
    AndroidView(factory = {
        WebView(context).apply {
            loadUrl(url)
            settings.javaScriptEnabled = true
        }
    }, modifier = Modifier
        .fillMaxSize()
        .padding(16.dp), update = { it.loadUrl(url) })
}