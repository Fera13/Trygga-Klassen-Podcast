package com.example.tryggaklassenpod.externalResources

import android.webkit.WebSettings
import android.webkit.WebView
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView


@Composable
fun HighPriorityPodcast(){
    AndroidView(
        modifier = Modifier
            .fillMaxWidth(),
        factory = { context ->
            val webView = WebView(context)
            val webSettings = webView.settings
            webSettings.javaScriptEnabled = true

            // priority podcast
            webView.loadUrl("https://player-widget.mixcloud.com/widget/iframe/?hide_cover=1&mini=1&light=1&feed=%2FLotusmodellen%2Fsommarpratare-3-juli-2015%2F")

            webView
        }
    )
}