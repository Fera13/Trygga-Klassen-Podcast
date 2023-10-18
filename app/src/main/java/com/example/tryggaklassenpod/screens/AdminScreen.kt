package com.example.tryggaklassenpod.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.tryggaklassenpod.R
import com.example.tryggaklassenpod.ui.theme.TryggaKlassenPodTheme
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminScreen(navController: NavController) {
    TryggaKlassenPodTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Welcome! Let us make a safe haven for children one podcast at a time",
                    style = MaterialTheme.typography.headlineSmall,
                    modifier = Modifier.padding(16.dp)
                        .fillMaxWidth()
                        .wrapContentHeight(),
                    textAlign = TextAlign.Center
                )
            }
            Spacer(modifier = Modifier.height(16.dp))

            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                LazyRow(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    item {
                        Card(
                            modifier = Modifier
                                .width(180.dp)
                                .padding(8.dp),
                            onClick = {
                                navController.navigate("uploadPodcast")
                            }
                        ) {
                            Column(
                                modifier = Modifier.padding(16.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                val upload = painterResource(id = R.drawable.upload)

                                Icon(
                                    painter = upload,
                                    contentDescription = "Upload Podcast"
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(
                                    text = "Upload Podcast",
                                    style = MaterialTheme.typography.bodyMedium)
                            }
                        }
                    }
                    item {
                        Card(
                            modifier = Modifier
                                .width(180.dp)
                                .padding(8.dp),

                            onClick = {
                                navController.navigate("CommentReviewScreen")
                            }
                        ) {
                            Column(
                                modifier = Modifier.padding(16.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                val review = painterResource(id = R.drawable.review)
                                Icon(
                                    painter = review,
                                    contentDescription = "Review Comments"
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(
                                    text = "Review Comments",
                                    style = MaterialTheme.typography.bodyMedium
                                )
                            }
                        }
                    }
                }
                Card(
                    modifier = Modifier
                        .width(180.dp)
                        .padding(8.dp),

                    onClick = {
                        navController.navigate("PodcastsList")
                    }
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        val oldPodcasts = painterResource(id = R.drawable.old_podcast)
                        Icon(
                            painter = oldPodcasts,
                            contentDescription = "Old Podcasts"
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "Old Podcasts",
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }
            }
        }
    }
}
