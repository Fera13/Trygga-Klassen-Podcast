package com.example.tryggaklassenpod.screens

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.tryggaklassenpod.dataClasses.Comments
import com.example.tryggaklassenpod.helperFunctions.fetchUnapprovedComments
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.core.Context

@Composable
fun CommentReviewScreen(episodeId: Int) {
    var commentsWithTitles by remember { mutableStateOf(emptyList<Pair<String, Comments>>()) }
    val context = LocalContext.current

    LaunchedEffect(episodeId) {
        fetchUnapprovedComments(episodeId) { fetchedCommentsWithTitles ->
            commentsWithTitles = fetchedCommentsWithTitles
        }
    }

    val uniqueEpisodeTitles = commentsWithTitles.map { it.first }.distinct()

    LazyColumn {
        uniqueEpisodeTitles.forEach { episodeTitle ->
            val commentsForEpisode = commentsWithTitles.filter { it.first == episodeTitle }.map { it.second }

            item {
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = episodeTitle,
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(8.dp)
                )
            }

            itemsIndexed(commentsForEpisode) { commentIndex, comment ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(modifier = Modifier.weight(1f)) {
                        Text(
                            text = comment.comment ?: "",
                            style = MaterialTheme.typography.bodyMedium,
                            modifier = Modifier.padding(8.dp)
                        )
                    }
                    Column(
                        modifier = Modifier
                            .padding(16.dp),
                    ) {
                        Button(
                            onClick = { publishComment(episodeId, comment, context)
                                fetchUnapprovedComments(episodeId) { fetchedCommentsWithTitles ->
                                    commentsWithTitles = fetchedCommentsWithTitles
                                }
                                      },
                            modifier = Modifier.padding(8.dp),


                        ) {
                            Text("Publish")
                        }
                        Button(
                            onClick = { deleteComment(episodeId, comment, context)
                                fetchUnapprovedComments(episodeId) { fetchedCommentsWithTitles ->
                                    commentsWithTitles = fetchedCommentsWithTitles
                                }},
                            modifier = Modifier.padding(8.dp)
                        ) {
                            Text("Delete")
                        }
                    }
                }
            }
        }
    }
}


fun publishComment(episodeId: Int, comment: Comments, context: android.content.Context) {
    val database = FirebaseDatabase.getInstance()
    val episodeReference = database.getReference("podcast").child("episodes").child(episodeId.toString())
    val commentReference = episodeReference.child("comments").child(comment.commentId.toString())

    // Update the comment's 'approved' status to 'true'
    comment.approved = true
    commentReference.setValue(comment)
        .addOnSuccessListener {
            Toast.makeText(context, "Comment published", Toast.LENGTH_SHORT).show()
        }
        .addOnFailureListener { exception ->
            // Handle failure if needed
        }
}

fun deleteComment(episodeId: Int, comment: Comments,context: android.content.Context ) {
    val database = FirebaseDatabase.getInstance()
    val episodeReference =
        database.getReference("podcast").child("episodes").child(episodeId.toString())
    val commentReference = episodeReference.child("comments").child(comment.commentId.toString())
    commentReference.removeValue()
        .addOnSuccessListener {
            Toast.makeText(context, "deleted successfully", Toast.LENGTH_LONG).show()
        }
}

