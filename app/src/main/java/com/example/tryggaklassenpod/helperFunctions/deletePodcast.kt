package com.example.tryggaklassenpod.helperFunctions

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.ui.platform.LocalContext
import com.google.firebase.database.FirebaseDatabase

fun deletePodcast(podcastId: String, context: Context) {
    // Assuming you have a reference to the Firebase database
    val database = FirebaseDatabase.getInstance()
    val contract = ActivityResultContracts.GetContent()

    // Reference to the specific podcast based on the podcastId
    val podcastReference = database.getReference("podcast").child("episodes").child(podcastId)

    // Remove the value from the database
    podcastReference.removeValue()
        .addOnSuccessListener {
            Toast.makeText(context, "deleted successfully", Toast.LENGTH_LONG).show()
        }
        .addOnFailureListener { exception ->
            Log.e("Error", "Error deleting the podcast")
        }
}
