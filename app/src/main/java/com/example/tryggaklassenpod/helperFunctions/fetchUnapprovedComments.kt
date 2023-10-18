package com.example.tryggaklassenpod.helperFunctions

import com.example.tryggaklassenpod.dataClasses.Comments
import com.google.firebase.database.FirebaseDatabase

fun fetchUnapprovedComments(episodeId: Int, onCommentsFetched: (List<Comments>) -> Unit) {
    val database = FirebaseDatabase.getInstance()
    val episodeReference = database.getReference("podcast").child("episodes").child(episodeId.toString())

    val unapprovedComments = mutableListOf<Comments>()
    episodeReference.child("comments").get().addOnSuccessListener { snapshot ->
        snapshot.children.forEach { commentSnapshot ->
            val comment = commentSnapshot.getValue(Comments::class.java)
            if (comment != null && !comment.approved!!) {
                unapprovedComments.add(comment)
            }
        }
        onCommentsFetched(unapprovedComments)
    }.addOnFailureListener { exception ->
        // Handle the failure
    }
}

