package com.example.tryggaklassenpod.screens

import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
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
import com.example.tryggaklassenpod.dataClasses.Episode
import com.google.firebase.database.FirebaseDatabase
import com.example.tryggaklassenpod.helperFunctions.AudioUploader
import com.google.firebase.storage.FirebaseStorage
import android.media.MediaMetadataRetriever
import android.media.MediaPlayer
import androidx.compose.foundation.layout.Column
import com.example.tryggaklassenpod.helperFunctions.Authentication
import com.example.tryggaklassenpod.helperFunctions.ImageUploader
import com.google.firebase.database.core.Context

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UploadPodcast(navController: NavController) {
    var podcastName by remember { mutableStateOf("") }
    var podcastDescription by remember { mutableStateOf("") }
    var downloadUrl by remember { mutableStateOf("") }
    var storageLocationUrl by remember { mutableStateOf("") }
    var imageUrl by remember { mutableStateOf("") }// Declare a mutableState variable to store the download URL
    var selectedFileName by remember { mutableStateOf<String?>(null) }
    var selectedImageFileName by remember { mutableStateOf<String?>(null) }
    var isUploading by remember { mutableStateOf(false) }
    var duration by remember { mutableStateOf("") }
    val durationInt = duration.toIntOrNull() ?: 0 // Convert the duration to an integer, defaulting to 0 if the input is not a valid number


    val database = FirebaseDatabase.getInstance()
    val databaseReference = database.getReference("podcasts")
    val storage = FirebaseStorage.getInstance()
    val storageReference = storage.getReference("podcasts")
    val filePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { audioUri: Uri? ->
        // Check if audioUri is not null, indicating that the user picked an audio file
        if (audioUri != null) {
            selectedFileName = audioUri.lastPathSegment

            // Create an instance of AudioUploader

            val audioStorageRef = storageReference.child("audios/${selectedFileName}")
            storageLocationUrl = audioStorageRef.toString()
            Log.d("YourTag", "downloadUrl: $storageLocationUrl")

            // Retrieve user email and password
            val email = "anam@example.com"
            val password = "password"

            val audioUploader = AudioUploader(storageReference, Authentication())


            // Call the uploadAudio function with the selected audioUri
            audioUploader.uploadAudio(podcastName, email, password, audioUri) { newDownloadUrl ->
                // Handle the download URL here if needed
                if (newDownloadUrl != null) {
                    downloadUrl = newDownloadUrl.toString()
                    Log.d("YourTag", "downloadUrl: $downloadUrl")
                    // The audio has been uploaded successfully, and downloadUrl contains the URL
                }
            }
        }
    }
    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { imageUri: Uri? ->
        if (imageUri != null) {
            Log.d("YourTag", "imageUri: $imageUri")
            // Set the selectedImageFileName to the name of the selected image file
            selectedImageFileName = imageUri.lastPathSegment
            val imageStorageRef = storageReference.child("images/${selectedImageFileName}")
            imageUrl = imageStorageRef.toString()
            val email = "anam@example.com"
            val password = "password"

            val imageUploader = ImageUploader(storageReference, Authentication())
            // Call the uploadAudio function with the selected audioUri
            imageUploader.uploadImage(podcastName, email, password, imageUri) { newDownloadUrl ->
                // Handle the download URL here if needed
                if (newDownloadUrl != null) {
                    downloadUrl = newDownloadUrl.toString()
                    Log.d("YourTag", "downloadUrl: $downloadUrl")
                    // The image has been uploaded successfully, and downloadUrl contains the URL
                }
            }

        }

    }


    Column(
        modifier = Modifier.padding(16.dp)
    )
    {
        Text(
            text = "Add the title/name of the podcast",
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(16.dp)
        )
        OutlinedTextField(
            value = podcastName,
            onValueChange = { newValue ->
                podcastName = newValue
            },
            label = { Text("Title") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Add the summary/description of the podcast",
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(16.dp)
        )

        OutlinedTextField(
            value = podcastDescription,
            onValueChange = { newValue ->
                podcastDescription = newValue
            },
            label = { Text("Description ") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        )
        Log.d("YourTag", "podcastName: $podcastName")
        Log.d("YourTag", "podcastDescription: $podcastDescription")

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Add the length of the podcast in seconds",
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(16.dp)
        )
        OutlinedTextField(
            value = duration,
            onValueChange = { newValue ->
                duration = newValue
            },
            label = { Text("Length(seconds)") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        if (selectedFileName != null) {
            OutlinedTextField(
                value = selectedFileName!!,
                onValueChange = { /* No-op, as this is read-only */ },
                modifier = Modifier.fillMaxWidth().padding(8.dp)
            )
//
        }
        Button(
            onClick = {
                filePickerLauncher.launch("audio/*")
                }
        ) {
            Text(text = "Upload podcast")
        }
        // Display the selected image file name (if it's not null) in an OutlinedTextField
        if (selectedImageFileName != null) {
            OutlinedTextField(
                value = selectedImageFileName!!,
                onValueChange = {
                    // No-op, read-only
                },
                label = { Text("Selected Image File") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            )
        }
        Button(
            onClick = {
                imagePickerLauncher.launch("image/*")

            }
        ) {
            Text(text = "Select Image File")
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(
                onClick = {
                    if (downloadUrl.isNotEmpty()) {
                        val newEpisodeReference = databaseReference
                            .child("episodes")
                            .push()

                        // Create episode data
                        val episodeData = Episode(
                            id = 112,
                            episodeUrl = storageLocationUrl,
                            duration = durationInt,
                            imageUrl = imageUrl,
                            title = podcastName,
                            description = podcastDescription,
                        )

                        // Set episode data
                        newEpisodeReference.setValue(episodeData).addOnSuccessListener {
                            podcastName = ""
                            podcastDescription = ""
                            duration = ""
                            selectedFileName = null
                            selectedImageFileName = null
                        }.addOnFailureListener { exception ->
                            Log.e("YourTag", "Error adding episode: ${exception.message}")
                        }
                    }else {
                        isUploading = false // Hide the loading indicator
                    }
                }
            ) {
                Text(text = "Done")
            }
        }
    }
}