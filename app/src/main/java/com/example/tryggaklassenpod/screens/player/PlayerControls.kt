package com.example.tryggaklassenpod.screens.player

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.tryggaklassenpod.R
import com.example.tryggaklassenpod.helperFunctions.PodcastPlayerManager
import com.example.tryggaklassenpod.helperFunctions.toHoursMinuteSeconds
import com.example.tryggaklassenpod.screens.PodcastViewModel
import kotlinx.coroutines.delay


@Composable
fun PlayerControllerArea(
    episodeUrl: String,
    episodeDuration: Int,
    viewModel: PodcastViewModel
) {
//    val player = viewModel.player
//    val player = remember { PodcastPlayerManager() }
//    var isPlaying by remember { mutableStateOf(false) }
//    var newPosition by remember { mutableIntStateOf(0) }
//    val (sliderPosition, setSliderPosition) = remember { mutableFloatStateOf(0F) }

    if (viewModel.newPosition >= episodeDuration) {
        viewModel.isPlaying = false
    }
//    DisposableEffect(episodeUrl) {
//        onDispose {
//            isPlaying = false
//            player.releasePlayer()
//        }
//    }
//    LaunchedEffect(isPlaying){
//        while(isPlaying){
//            newPosition = player.getCurrentInSeconds()
//            setSliderPosition(newPosition.toFloat() / episodeDuration.toFloat())
//            delay(500)
//        }
//    }
    LaunchedEffect(viewModel.isPlaying) {
        while (viewModel.isPlaying) {
            viewModel.newPosition = viewModel.player.getCurrentInSeconds()
            viewModel.sliderPosition = viewModel.newPosition.toFloat() / episodeDuration.toFloat()
            delay(500)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Slider(
            value = viewModel.sliderPosition,
            enabled = false,
            onValueChange = {
                if (viewModel.player.canPlayerStart()){
                    viewModel.sliderPosition = it
                    viewModel.player.seekTo((it * episodeDuration).toInt())
                }
            },
            colors = SliderDefaults.colors(
                thumbColor = MaterialTheme.colorScheme.secondary,
                activeTrackColor = MaterialTheme.colorScheme.primary,
                inactiveTrackColor = MaterialTheme.colorScheme.secondary,
            ),
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = viewModel.newPosition.toHoursMinuteSeconds())
            Text(text = episodeDuration.toHoursMinuteSeconds())
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = { viewModel.player.replay10Seconds() },
                modifier = Modifier.size(45.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.replay10),
                    contentDescription = stringResource(R.string.replay10),
                    modifier = Modifier.size(45.dp),
                    tint = MaterialTheme.colorScheme.secondary
                )
            }
            IconButton(
                onClick = {
                    if (viewModel.isPlaying) {
                        viewModel.player.pauseEpisode()
                    } else {
                        viewModel.player.playEpisode(episodeUrl)
                    }
                    viewModel.isPlaying = !viewModel.isPlaying
                },
                modifier = Modifier.size(70.dp)
            ) {
                Icon(
                    painter = if (viewModel.isPlaying) {
                        painterResource(R.drawable.pause)
                    } else {
                        painterResource(R.drawable.play)
                    },
                    contentDescription = stringResource(R.string.play_pause),
                    modifier = Modifier.size(70.dp),
                    tint = MaterialTheme.colorScheme.secondary,
                )
            }
            IconButton(
                onClick = { viewModel.player.forward30Seconds() },
                modifier = Modifier.size(45.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.forward30),
                    contentDescription = stringResource(R.string.forward30),
                    modifier = Modifier.size(45.dp),
                    tint = MaterialTheme.colorScheme.secondary
                )
            }
        }
    }
}
