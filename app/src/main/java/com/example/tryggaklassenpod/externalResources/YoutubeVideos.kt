package com.example.tryggaklassenpod.externalResources


import android.content.Intent
import android.os.Bundle
import android.webkit.WebView
import androidx.activity.ComponentActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Button
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.tryggaklassenpod.R

class YouTubeIframeActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val webView = WebView(this)
        val webSettings = webView.settings
        webSettings.javaScriptEnabled = true

        val iframeCode = "<iframe width=\"560\" height=\"315\" src=\"https://www.youtube.com/embed/Li1BjZg0PD4\" frameborder=\"0\" allowfullscreen></iframe>"

        webView.loadData(iframeCode, "text/html", "utf-8")
        setContentView(webView)
    }
}


@Composable
fun CallYoutubeInstance() {
    val buttonClicked = remember { mutableStateOf(false) }
    val myContext = LocalContext.current

    // UI
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Image(
            painter = painterResource(id = R.drawable.youtube_sample),
            contentDescription = null,
            modifier = Modifier
                .fillMaxSize()
        )

        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.padding(30.dp))
            FloatingActionButton(
                onClick = {
                    // buttonClicked state
                    buttonClicked.value = true
                },
                modifier = Modifier.padding(16.dp),
                containerColor = Color.Red,
                contentColor = Color.White
            ) {
                Icon(Icons.Default.PlayArrow, "playbutton", modifier = Modifier.size(30.dp))
            }

            // if the button was clicked
            if (buttonClicked.value) {
                // YouTubeIframeActivity
                val intent = Intent(LocalContext.current, YouTubeIframeActivity::class.java)
                LocalContext.current.startActivity(intent)
            }
        }
    }
}



