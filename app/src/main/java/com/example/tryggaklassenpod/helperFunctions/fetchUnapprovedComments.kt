package com.example.tryggaklassenpod.helperFunctions

import android.util.Log
import com.example.tryggaklassenpod.dataClasses.Comments
import com.google.firebase.database.FirebaseDatabase

fun fetchUnapprovedComments(episodeId: Int, onCommentsFetched: (List<Pair<String, Comments>>) -> Unit) {
    val database = FirebaseDatabase.getInstance()
    val episodeReference = database.getReference("podcast").child("episodes").child(episodeId.toString())

    val unapprovedComments = mutableListOf<Pair<String, Comments>>()

    episodeReference.get().addOnSuccessListener { episodeSnapshot ->
        val episodeTitle = episodeSnapshot.child("title").getValue(String::class.java)
        episodeReference.child("comments").get().addOnSuccessListener { commentSnapshot ->
            commentSnapshot.children.forEach { commentSnapshot ->
                val comment = commentSnapshot.getValue(Comments::class.java)
                if (comment != null && !comment.approved!!) {
                    unapprovedComments.add(Pair(episodeTitle, comment) as Pair<String, Comments>)
                }
            }
            onCommentsFetched(unapprovedComments)
        }.addOnFailureListener { innerException ->
            Log.e("FetchCommentsInner", "Failed to fetch comments: ${innerException.message}")
        }
    }.addOnFailureListener { outerException ->
        Log.e("FetchCommentsOuter", "Failed to fetch comments: ${outerException.message}")
    }
}

