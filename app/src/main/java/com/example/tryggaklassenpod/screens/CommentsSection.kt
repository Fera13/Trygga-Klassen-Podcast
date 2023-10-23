package com.example.tryggaklassenpod.screens

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Button
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.tryggaklassenpod.R
import com.example.tryggaklassenpod.dataClasses.Comments
import com.example.tryggaklassenpod.dataClasses.episodesList
import com.example.tryggaklassenpod.helperFunctions.UsernameGenerator
import com.example.tryggaklassenpod.helperFunctions.getCreatedAtFormatted

@Composable
fun CommentsSection(comments: List<Comments>, episodeId: Int, viewModel: PodcastViewModel) {

    var isShowing by remember { mutableStateOf(false)}

    Column(
        modifier = Modifier.padding(4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            Text(
                text = stringResource(R.string.comments),
                style = MaterialTheme.typography.headlineSmall,
            )
            Column(
                modifier = Modifier.width(55.dp),
                horizontalAlignment = Alignment.End
            ) {
                Text(
                    text = if (isShowing) "Hide" else "Show",
                    modifier = Modifier
                        .padding(top = 4.dp, start = 8.dp)
                        .clickable { isShowing = !isShowing },
                )
            }
            Column {
                Icon(
                    painter = if (isShowing) {
                        painterResource(R.drawable.expand_less)
                    } else {
                        painterResource(R.drawable.expand_more)
                    },
                    contentDescription = stringResource(R.string.expand_comments),
                    modifier = Modifier
                        .padding(top = 6.dp)
                        .size(20.dp)
                        .clickable { isShowing = !isShowing },
                    tint = MaterialTheme.colorScheme.secondary,
                )
            }
        }

        if (isShowing) {
            CommentsList(comments = comments)
        }

        Spacer(modifier = Modifier.height(16.dp))
        ReplySection(episodeId = episodeId, viewModel = viewModel)

        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = stringResource(R.string.comments_screening_text),
            style = MaterialTheme.typography.bodySmall,
        )
    }
}

@Composable
fun CommentsList(comments: List<Comments>) {
    for (comment in comments) {
        if (comment?.approved == true){
            CommentCard(comment = comment)
        }
    }
}

@Composable
fun CommentCard(comment: Comments) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Column {
            Icon(
                painter = painterResource(id = R.drawable.person),
                contentDescription = stringResource(R.string.placeholder_comments),
                modifier = Modifier
                    .size(40.dp)
                    .padding(end = 4.dp),
                tint = MaterialTheme.colorScheme.primary
            )
        }
        Column {
            comment.author?.let {
                Text(
                    text = it,
                    color = MaterialTheme.colorScheme.primary,
                    style = MaterialTheme.typography.bodyLarge
                )
            }
            comment.createdAt?.let {
                Text(
                    text = it.getCreatedAtFormatted(),
                    color = MaterialTheme.colorScheme.secondary,
                    style = MaterialTheme.typography.bodySmall,
                )
            }
            comment.comment?.let {
                Text(
                    text = it,
                    modifier = Modifier.padding(top = 4.dp, bottom = 4.dp)
                )
            }
        }
    }
}

@Composable
fun ReplySection(episodeId: Int, viewModel: PodcastViewModel) {
    Text(
        text = stringResource(R.string.leave_a_reply),
        style = MaterialTheme.typography.headlineSmall,
        modifier = Modifier.padding(bottom = 8.dp)
    )
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp)
    ) {
        WriteComment(episodeId = episodeId, viewModel = viewModel)
    }
}


@Composable
fun WriteComment(episodeId: Int, viewModel: PodcastViewModel, modifier: Modifier = Modifier) {
    var reply by remember { mutableStateOf("") }
    var username by remember {  mutableStateOf("") }
    val commentPlaceholder = stringResource(R.string.write_here)
    val context = LocalContext.current
    val toast = Toast.makeText(
        context, stringResource(R.string.your_comment_was_added),
        Toast.LENGTH_LONG)
    val errorToast = Toast.makeText(
        context, stringResource(R.string.error_has_occurred),
        Toast.LENGTH_LONG)
    val usernamePlaceholder = stringResource(R.string.username)
    val nameGenerator = UsernameGenerator()

    OutlinedCard(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface,
        ),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.secondary.copy(alpha = 0.5f)),
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 8.dp)
    ) {
        BasicTextField(
            value = username,
            onValueChange = { username = it },
            maxLines = 1,
            singleLine = true,
            decorationBox = { innerTextField ->
                Box(modifier = modifier) {
                    if (username.isEmpty()) {
                        Text(
                            text = usernamePlaceholder,
                            color = LocalContentColor.current.copy(alpha = 0.5f),
                        )
                    }
                    Row(
                        horizontalArrangement = Arrangement.End,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(
                            modifier = Modifier.fillMaxWidth(0.75f)
                        ) {
                            innerTextField()
                        }
                        Column(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalAlignment = Alignment.End
                        ) {
                            Text(
                                text = "Generate",
                                color = MaterialTheme.colorScheme.primary,
                                modifier = Modifier
                                    .clickable {
                                        username = nameGenerator.generateUsername()
                                    }
                            )
                        }
                    }
                }
            },
            modifier = modifier.padding(vertical = 16.dp, horizontal = 8.dp),
        )
    }

    OutlinedCard(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface,
        ),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.secondary.copy(alpha = 0.5f)),
        modifier = Modifier
            .fillMaxWidth()
    ) {
        BasicTextField(
            value = reply,
            onValueChange = { reply = it },
            maxLines = 10,
            decorationBox = { innerTextField ->
                Box(modifier = modifier) {
                    if (reply.isEmpty()) {
                        Text(
                            text = commentPlaceholder,
                            color = LocalContentColor.current.copy(alpha = 0.5f),
                        )
                    }
                    innerTextField()
                }
            },
            modifier = modifier.padding(vertical = 16.dp, horizontal = 8.dp),
        )
    }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp),
    ) {
        Button(
            onClick = {
                viewModel.newCommentOnEpisode(episodeId, reply, username)
                if (viewModel.commentUiState.value == SubmitCommentUiState.Error){
                    errorToast.show()
                }
                if (viewModel.commentUiState.value == SubmitCommentUiState.Success) {
                    toast.show()
                }
                reply = ""
                username = ""
                viewModel.commentUiState.value = SubmitCommentUiState.Initial
            },
            modifier = Modifier.padding(end = 8.dp),
            enabled = reply.isNotEmpty() && username.isNotEmpty()
        ) {
            Text(
                text = stringResource(R.string.add_comment)
            )
        }
        FilledTonalButton(
            onClick = {
                reply = ""
                username = ""
            },
        ) {
            Text(text = stringResource(R.string.cancel))
        }
    }
}


@Preview(showSystemUi = true, showBackground = true)
@Composable
fun CommentsSectionPreview() {
    val comments = episodesList[0]?.comments
    if (comments != null) {
        CommentsSection(
            comments = comments,
            episodeId = 0,
            viewModel = PodcastViewModel()
        )
    }
}

@Preview(showBackground = true)
@Composable
fun CommentsCardPreview() {
    val comments = episodesList[0]?.comments
    val comment = comments?.get(0)
    if (comment != null) {
        CommentCard(comment = comment)
    }
}
