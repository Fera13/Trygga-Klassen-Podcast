package com.example.tryggaklassenpod.screens

import android.widget.Toast
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.example.tryggaklassenpod.dataClasses.Comments
import com.example.tryggaklassenpod.helperFunctions.fetchUnapprovedComments
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.core.Context

@Composable
fun CommentReviewScreen(episodeId: Int) {
    var comments by remember { mutableStateOf(emptyList<Comments>()) }
    val context = LocalContext.current
    LaunchedEffect(episodeId) {
        fetchUnapprovedComments(episodeId) { fetchedComments ->
            comments = fetchedComments
        }
    }

    LazyColumn {
        itemsIndexed(comments) { index, comment ->
            // Display comment details here
            Text(text = comment.comment.toString())
            Button(
                onClick = { publishComment(episodeId, comment, context ) },
                
            ) {
                Text("Publish")
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
            // Show a Toast message indicating that the comment has been published
            Toast.makeText(context, "Comment published", Toast.LENGTH_SHORT).show()
        }
        .addOnFailureListener { exception ->
            // Handle failure if needed
        }
}

