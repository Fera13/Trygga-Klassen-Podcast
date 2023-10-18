package com.example.tryggaklassenpod.screens

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.tryggaklassenpod.dataClasses.Episode
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import com.example.tryggaklassenpod.helperFunctions.getPodcastDetails
import com.google.firebase.database.FirebaseDatabase

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditPodcasts(navController: NavController, podcastId: String) {

    val database = FirebaseDatabase.getInstance()
    val databaseReference = database.getReference("podcast")

    var title by remember {  mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var imageUrl by remember { mutableStateOf("") }
    var episodeUrl by remember { mutableStateOf("") }
    val context = LocalContext.current

    // Fetch the initial data
    LaunchedEffect(podcastId) {
        getPodcastDetails(podcastId) { episode ->
            title = episode.title
            description = episode.description
            imageUrl = episode.imageUrl
            episodeUrl = episode.episodeUrl
            Log.d("episode", "episode $episode")
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextField(
            value = title,
            onValueChange = { title = it },
            label = { Text("Title") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        )

        OutlinedTextField(
            value = description,
            onValueChange = { description = it },
            label = { Text("Description") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text("Image URL: $imageUrl")
        Spacer(modifier = Modifier.height(8.dp))
        Text("Episode URL: $episodeUrl")

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                // Update the podcast details in the database
                updateEpisodeInDatabase(
                    Episode(
                        id = podcastId.toInt(), // Assuming podcastId is in string format
                        title = title,
                        description = description,
                        imageUrl = imageUrl, // Retain the existing imageUrl
                        episodeUrl = episodeUrl // Retain the existing episodeUrl
                    )
                )
                Toast.makeText(context, "Updated successfully", Toast.LENGTH_LONG).show()
                navController.popBackStack() // back to the previous screen
            },
            modifier = Modifier.padding(8.dp)
        ) {
            Text("Submit")
        }
    }
}

fun updateEpisodeInDatabase(updatedEpisode: Episode) {
    // Update the episode with the new information in the database
    // Example code:
    val database = FirebaseDatabase.getInstance()
    val databaseReference = database.getReference("podcast")
    databaseReference.child("episodes").child(updatedEpisode.id.toString()).setValue(updatedEpisode)
        .addOnSuccessListener {

        }
        .addOnFailureListener { exception ->
            // Handle failure if needed
        }
}




