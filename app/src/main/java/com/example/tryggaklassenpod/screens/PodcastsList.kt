package com.example.tryggaklassenpod.screens

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.tryggaklassenpod.dataClasses.Episode
import com.example.tryggaklassenpod.helperFunctions.EpisodeFetcher
import com.example.tryggaklassenpod.helperFunctions.deletePodcast

@Composable
fun PodcastsList(navController: NavController) {
    val episodeFetcher = EpisodeFetcher()
    Log.d("episodeFetcher", "episodeFetcher $episodeFetcher")
    val context = LocalContext.current

    var episodes by remember { mutableStateOf(emptyList<Episode>()) }

    LaunchedEffect(Unit) {
        episodeFetcher.fetchEpisodes { fetchedEpisodes ->
            episodes = fetchedEpisodes as List<Episode>
        }

    }
    Log.d("episodeFetcher", "episodeFetcher $episodeFetcher")

    LazyColumn {
        items(episodes) { episode ->

            Row(
                modifier = Modifier.fillMaxWidth(),
//                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically ,

            ) {
                Column(
                    modifier = Modifier.weight(2f)
                ) {
                Text(text =
                episode.title,
                    modifier = Modifier.padding(16.dp),
                    style = MaterialTheme.typography.bodyMedium,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                }
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(16.dp)
                ) {
                Button(
                    onClick = {navController.navigate("editPodcasts/${episode.id}") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp)
                ) {
                    Text("Edit")
                }
                Button(
                    onClick = {
                        val podcastId = episode.id.toString()
                        deletePodcast(podcastId, context)
                       },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Delete")
                }
            }
        }
    }
}
}
