package com.example.tryggaklassenpod.helperFunctions

import android.net.Uri
import com.google.firebase.storage.StorageReference


class ImageUploader(private val storageRef: StorageReference) {
    fun uploadImage(imageUri: Uri, callback: (String?) -> Unit) {
        // Generate a unique storage reference for the image file
        val imageStorageRef = storageRef.child("images/${imageUri.lastPathSegment}")

        // Upload the image file to Firebase Storage
        val uploadTask = imageStorageRef.putFile(imageUri)

        // Register observers to listen for when the upload is done or if it fails
        uploadTask.addOnSuccessListener { taskSnapshot ->
            // Image file uploaded successfully
            val downloadUrl = taskSnapshot.metadata?.reference?.downloadUrl.toString()
            callback(downloadUrl)
        }.addOnFailureListener { exception ->
            callback(null) // Handle the failure case by passing null
        }
    }
}
