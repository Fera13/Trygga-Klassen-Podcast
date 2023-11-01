package com.example.tryggaklassenpod.screens

import android.content.Context
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
import androidx.compose.ui.platform.LocalContext
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
import android.widget.Toast
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import com.example.tryggaklassenpod.MainActivity
import com.example.tryggaklassenpod.helperFunctions.getNextId
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.security.AccessController.getContext


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
    var imageLatestUrl by remember { mutableStateOf("") }
    var audioLatestUrl by remember { mutableStateOf("") }
    val database = FirebaseDatabase.getInstance()
    // Create a root reference
    val databaseReference = database.getReference("podcast")
    val storage = FirebaseStorage.getInstance()
    val storageReference = storage.getReference("podcasts")
    val context = LocalContext.current

    val audioCoroutineScope = rememberCoroutineScope()
    val filePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { audioUri: Uri? ->
        // Check if audioUri is not null, indicating that the user picked an audio file
        if (audioUri != null) {
            selectedFileName = audioUri.lastPathSegment

            // Create an instance of AudioUploader

            val audioStorageRef = storageReference.child("audios/${selectedFileName}")
            storageLocationUrl = audioStorageRef.toString()
            Log.d("YourTag", "AudiodownloadUrl: $storageLocationUrl")

            val audioUploader = AudioUploader(storageReference)

            // Call the uploadAudio function with the selected audioUri
            audioUploader.uploadAudio(audioUri) { newDownloadUrl ->
                // Handle the download URL here if needed
                if (newDownloadUrl != null) {
                    downloadUrl = newDownloadUrl.toString()
                    Log.d("YourTag", "AudiodownloadUrlsss: $downloadUrl")
                    // The audio has been uploaded successfully, and downloadUrl contains the URL
                }
                // Use coroutines to await the downloadUrl task
                audioCoroutineScope.launch {
                    try {
                        val uri = audioStorageRef.downloadUrl.await()  // await the result
                        audioLatestUrl = uri.toString()
                        Log.d("AudioInSuccessLatestUrll", "AudioLatestUrl:$audioLatestUrl")

                        // Continue with your next logic here...

                    } catch (e: Exception) {
                        Log.e("imageError", "Error fetching image URL: ${e.localizedMessage}")
                    }
                }
            }
        }
    }
   //image launching, saving and converting the storage location
    val coroutineScope = rememberCoroutineScope()

    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { imageUri: Uri? ->
        if (imageUri != null) {
            Log.d("YourTag", "imageUri: $imageUri")
            selectedImageFileName = imageUri.lastPathSegment
            val imageStorageRef = storageReference.child("images/${selectedImageFileName}")
            imageUrl = imageStorageRef.toString()

            Log.d("Image Url", "imageUrl: $imageUrl")


            val imageUploader = ImageUploader(storageReference)
            // Call the uploadAudio function with the selected audioUri
            imageUploader.uploadImage(imageUri) { newDownloadUrl ->
                // Handle the download URL here if needed

                if (newDownloadUrl != null) {
                    downloadUrl = newDownloadUrl.toString()
                    Log.d("DownloadUrl", "downloadUrl: $downloadUrl")
                }

                // Use coroutines to await the downloadUrl task
                coroutineScope.launch {
                    try {
                        val ins = FirebaseStorage.getInstance()
                        val imageRef = ins.getReferenceFromUrl(imageUrl)
                        val uri = imageRef.downloadUrl.await()  // await the result
                        imageLatestUrl = uri.toString()
                        Log.d("imageInSuccessLatestUrl", "imageLatestUrl:$imageLatestUrl")


                    } catch (e: Exception) {
                        Log.e("imageError", "Error fetching image URL: ${e.localizedMessage}")
                    }
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
            text = "Upload the audio file for the podcast",
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(16.dp)
        )
        if (selectedFileName == null) {
            OutlinedTextField(
                value = "Audio",
                onValueChange = { },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            )
        } else {
            OutlinedTextField(
                value = selectedFileName!!,
                onValueChange = { },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            )
        }
        Button(
            onClick = {
                filePickerLauncher.launch("audio/*")
                }
        ) {
            Text(text = "Upload podcast")
        }
        Text(
            text = "Upload the image file for the podcast",
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(16.dp)
        )
        if (selectedImageFileName == null) {
            OutlinedTextField(
                value = "Image",
                onValueChange = { },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            )
        } else {
            OutlinedTextField(
                value = selectedImageFileName!!,
                onValueChange = { }, // read only
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
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Button(
                onClick = {
                    if (downloadUrl.isNotEmpty()) {
                        // Get the next available ID
                        getNextId { newId ->
                            val newEpisodeReference = databaseReference
                                .child("episodes")
                                .child(newId.toString())
                            Log.d("newIdinUpload","newId$newId")

                            // Create episode data
                            val episodeData = Episode(
                                id = newId,
                                episodeUrl = audioLatestUrl,
                                imageUrl = imageLatestUrl,
                                title = podcastName,
                                description = podcastDescription,
                            )
                            // Set episode data
                            newEpisodeReference.setValue(episodeData).addOnSuccessListener {
                                podcastName = ""
                                podcastDescription = ""
                                selectedFileName = null
                                selectedImageFileName = null
                                Toast.makeText(context, "Upload successful", Toast.LENGTH_SHORT).show()
                            }.addOnFailureListener { exception ->
                                Log.e("YourTag", "Error loading: ${exception.message}")
                            }
                        }
                    } else {
                        isUploading = false // Hide the loading indicator
                    }
                }
            ) {
                Text(text = "Done")
            }

        }
                }
            }
