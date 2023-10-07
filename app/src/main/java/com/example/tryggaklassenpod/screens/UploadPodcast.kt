package com.example.tryggaklassenpod.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

    @Composable
    fun UploadPodcast(navController: NavController) {
        var podcastName by remember { mutableStateOf("") }
        var podcastDescription by remember { mutableStateOf("") }

        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            BasicTextField(
                value = podcastName,
                onValueChange = { },
            )

            BasicTextField(
                value = podcastDescription,
                onValueChange = { },
            )

            Button(
                onClick = {
                }
            ) {
                Text(text = "Upload")
            }
        }
    }

