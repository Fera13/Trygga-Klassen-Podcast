package com.example.tryggaklassenpod.helperFunctions

import android.util.Log
import com.example.tryggaklassenpod.dataClasses.Comments
import com.example.tryggaklassenpod.dataClasses.Episode
import com.google.firebase.database.FirebaseDatabase

class EpisodeFetcher {
    private val database = FirebaseDatabase.getInstance()
    private val databaseReference = database.getReference("podcast")

    fun fetchEpisodes(callback: (List<Episode>) -> Unit) {
        val episodes: MutableList<Episode> = mutableListOf()
        databaseReference.child("episodes").get().addOnSuccessListener { snapshot ->
            snapshot.children.mapNotNull {
                val episode = it.getValue(Episode::class.java)
                episode?.id = it.key?.toInt() ?: 0
                if (episode != null) {
                    episodes.add(episode)
                }
                Log.d("episodes", "episodes $episodes")
            }
            callback(episodes)
        }.addOnFailureListener { exception ->
            // Handle the failure
        }
    }

    fun fetchCommentsForEpisode(episodeId: String, callback: (List<Comments>) -> Unit) {
        val comments: MutableList<Comments> = mutableListOf()
        val episodeRef = databaseReference.child("episodes").child(episodeId).child("comments")

        episodeRef.get().addOnSuccessListener { snapshot ->
            snapshot.children.mapNotNull {
                val comment = it.getValue(Comments::class.java)
                comment?.commentId = (it.key ?: "") as Int?
                if (comment != null) {
                    comments.add(comment)
                }
            }
            callback(comments)
        }.addOnFailureListener { exception ->
            // Handle the failure
        }
    }
}


