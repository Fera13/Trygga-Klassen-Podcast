package com.example.tryggaklassenpod.externalResources


import android.content.Intent
import android.os.Bundle
import android.webkit.WebSettings
import android.webkit.WebView
import androidx.activity.ComponentActivity
import androidx.appcompat.app.AppCompatActivity

class MixcloudEmbedActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val webView = WebView(this)
        val webSettings = webView.settings
        webSettings.javaScriptEnabled = true

        val iframeCode = "<iframe width=\"50%\" height=\"400\" src=\"https://player-widget.mixcloud.com/widget/iframe/?light=1&feed=%2FLotusmodellen%2Fsommarpratare-3-juli-2015%2F\" frameborder=\"0\"></iframe>"

        webView.loadData(iframeCode, "text/html", "utf-8")
        setContentView(webView)
    }
}


