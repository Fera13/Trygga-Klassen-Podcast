package com.example.tryggaklassenpod.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.material3.Text
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.ui.tooling.preview.Preview


@Composable
fun ErrorScreen(
    errorMessage: String,
    onRetry: () -> Unit,
    buttonIncluded: Boolean = true,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .wrapContentSize(Alignment.Center)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = errorMessage, color = MaterialTheme.colorScheme.primary)

            Spacer(modifier = Modifier.height(16.dp))
            if (buttonIncluded) {
                Button(
                    onClick = onRetry,
                    colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.primaryContainer)
                ) {
                    Text(
                        text = "Try Again",
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }
        }
    }
}





@Preview(showSystemUi = true, showBackground = true)
@Composable
fun ErrorScreenPreview() {
    ErrorScreen(
        errorMessage = "Something went wrong. Please try again.",
        onRetry = { }
    )
}