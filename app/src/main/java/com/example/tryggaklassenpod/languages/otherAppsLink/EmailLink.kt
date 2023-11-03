package com.example.tryggaklassenpod.languages.otherAppsLink

import android.content.Intent
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.text.ClickableText
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun EmailLinkCompose(email: String = "p-o@lotusmodellen.se") {

    // email launcher initialization
    val emailLauncher = rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { _ ->
        // You can add any further handling after returning from the email app
    }

    ClickableText(
        text = buildAnnotatedString {
            withStyle(SpanStyle(color = Color(0xFF4DD9E6), fontSize = 15.sp)) {
                append(email)
            }
        },
        onClick = { offset ->
            val clickedText = email.substring(offset, offset + 1)
            if (clickedText == "@" || clickedText == ".") {
                return@ClickableText
            }
            val emailIntent = Intent(Intent.ACTION_SENDTO)
            emailIntent.data = Uri.parse("mailto:$email")
            emailLauncher.launch(emailIntent)
        },
        modifier = Modifier.clickable { }

    )
}