package com.example.tryggaklassenpod.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.tryggaklassenpod.R
import com.example.tryggaklassenpod.dataClasses.Episode
import com.example.tryggaklassenpod.ui.components.ErrorScreen

@Composable
fun PlayerScreen(
    episodeId: Int?,
    viewModel: PodcastViewModel,
    goBack: () -> Unit,
    modifier: Modifier = Modifier,
) {
    if (episodeId == null) {
        ErrorScreen(
            errorMessage = "Something went wrong.",
            onRetry = {},
            buttonIncluded = false
        )
    }
    else {

        val episode: Episode? = episodeId?.let { viewModel.getEpisodeById(it) }
        if (episode != null) {
            viewModel.episodeUrl = episode.episodeUrl
            viewModel.player.preloadEpisode(episode.episodeUrl)
            viewModel.episodeFullDuration = viewModel.player.getFullDuration()
        }

        DisposableEffect(episodeId) {
            onDispose {
                viewModel.isPlaying = false
                viewModel.player.releasePlayer()
                viewModel.episodeUrl = ""
                viewModel.newPosition = 0
                viewModel.sliderPosition = 0.0f
                viewModel.episodeFullDuration = 0
            }
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 8.dp)
        ) {
            TopBarBack(goBack = goBack) {
                viewModel.player.releasePlayer()
            }

            LazyColumn(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .padding(start = 8.dp, end = 8.dp, bottom = 16.dp)

            ) {
                item {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .weight(10f),
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        episode?.imageUrl?.let {
                            EpisodeCoverImage(
                                imageUrl = it,
                                title = episode.title,
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(28.dp))
                }
                item {
                    Column(
                        modifier = Modifier
                            .weight(10f)
                    ) {
                        Card {
                            Spacer(modifier = Modifier.height(14.dp))
                            episode?.title?.let { EpisodeTitle(title = it) }
                            episode?.episodeUrl?.let {
                                PlayerControllerArea(
                                    episodeUrl = it,
                                     viewModel = viewModel
                                )
                            }
                            Spacer(modifier = Modifier.height(28.dp))
                        }
                    }
                }
                item {
                    Spacer(modifier = Modifier.height(14.dp))
                    episode?.description?.let { EpisodeDescription(description = it) }
                }
                item {
                    Spacer(modifier = Modifier.weight(1f))
                    episode?.comments?.let {
                        CommentsSection(
                            comments = it,
                            episodeId = episodeId,
                            viewModel = viewModel
                        )
                    }
                    Spacer(modifier = Modifier.height(28.dp))
                }
            }
        }
    }
}

@Composable
fun EpisodeTitle(title: String, modifier:Modifier = Modifier) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.headlineMedium,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
        Divider(
            modifier = Modifier
                .width(50.dp)
                .padding(top = 20.dp)
        )
    }
}

@Composable
fun EpisodeDescription(description: String, modifier:Modifier = Modifier) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 4.dp, vertical = 8.dp),
    ) {
        Text(
            text = stringResource(R.string.episode_heading),
            style = MaterialTheme.typography.headlineSmall,
        )
        Text(
            text = description,
            textAlign = TextAlign.Justify,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier
                .padding(top = 8.dp),
        )
    }
}

@Composable
fun EpisodeCoverImage(
    imageUrl: String,
    title: String,
    modifier: Modifier = Modifier
) {
    AsyncImage(
        model = imageUrl,
        contentDescription = title,
        contentScale = ContentScale.Fit,
        modifier = modifier
            .sizeIn(maxWidth = 400.dp, maxHeight = 400.dp)
            .aspectRatio(1f)
            .clip(MaterialTheme.shapes.medium)
    )
}

@Composable
private fun TopBarBack(goBack: () -> Unit,  onBack:() -> Unit) {
    Row(Modifier.fillMaxWidth()) {
        IconButton(onClick = {
            goBack.invoke()
            onBack.invoke()
            }
        ) {
            Icon(
                painter = painterResource(R.drawable.arrowback),
                contentDescription = stringResource(R.string.back)
            )
        }
        Spacer(Modifier.weight(1f))
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
private fun PlayerScreenPreview() {
    PlayerScreen(
        episodeId = 0,
        viewModel = PodcastViewModel(),
        goBack = { },
    )
}
