package com.example.tryggaklassenpod.helperFunctions

import com.example.tryggaklassenpod.dataClasses.Episode
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

fun getPodcastDetails(podcastId: String, callback: (Episode) -> Unit) {
    val database = FirebaseDatabase.getInstance()
    val podcastReference = database.getReference("podcast").child("episodes").child(podcastId)

    podcastReference.addValueEventListener(object : ValueEventListener {
        override fun onDataChange(snapshot: DataSnapshot) {
            val podcastDetails = snapshot.getValue(Episode::class.java)
            if (podcastDetails != null) {
                callback(podcastDetails)
            }
       }

        override fun onCancelled(error: DatabaseError) {
            // Handle the error if the data fetching is unsuccessful
        }
    })
}
