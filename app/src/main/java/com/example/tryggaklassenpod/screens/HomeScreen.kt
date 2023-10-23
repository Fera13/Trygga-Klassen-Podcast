package com.example.tryggaklassenpod.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.tryggaklassenpod.R
import com.example.tryggaklassenpod.dataClasses.Episode
import com.example.tryggaklassenpod.ui.components.ErrorScreen
import com.example.tryggaklassenpod.ui.components.LoadingScreen

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HomeScreen(podcastUiState: State<PodcastUiState>, navController: NavController){
    when (val podcast = podcastUiState.value) {
        is PodcastUiState.Loading -> LoadingScreen()
        is PodcastUiState.Success -> SuccessScreen(podcast.episodes, navController)
        is PodcastUiState.Error -> ErrorScreen(errorMessage = "Something went wrong. Please try again.",
            onRetry = { /* TODO somehow??? */ })
    }
}

@Composable
fun SuccessScreen(episodes: List<Episode>, navController: NavController) {
    LazyColumn {
        items(episodes) { item ->
            Card(
                modifier = Modifier
                    .padding(5.dp)
                    .height(150.dp)
                    .fillMaxSize()
                    .clickable {
                        navController.navigate(
                            route = "${Screen.PlayerScreen.route}/${item.id}"
                        ).toString()
                    }
            ) {
                Text(
                    text = item.title,
                    fontSize = 50.sp
                )
            }
        }
    }

}
//fun HomeScreen() {
//
//    Column (
//        modifier = Modifier.background(Color.Transparent)
//    ){
//        TopAppBar(
//
//            title = { Text(
//                text = "Trygga Klassen",
//                textAlign = TextAlign.Center,
//                modifier = Modifier.fillMaxWidth()
//            )},
//            navigationIcon = {
//                IconButton(
//                    onClick = { /* Handle menu icon click */ },
//                    ) {
//                    Icon(
//                        imageVector = Icons.Default.Menu,
//                        contentDescription = null,
//                        )
//                }
//            },
//
//            )
//
//        Column(
//            modifier = Modifier
//                .verticalScroll(rememberScrollState())
//                .fillMaxWidth()
//                .padding(5.dp)
//                ,
//            horizontalAlignment = Alignment.CenterHorizontally
//        ) {
//            Row (
//                verticalAlignment = Alignment.CenterVertically
//            ){
//                Image(
//                    painter = painterResource(id = R.drawable.placeholder),
//                    contentDescription = "sample",
//                    contentScale = ContentScale.FillBounds,
//
//                    )
//                Spacer(modifier = Modifier.padding(5.dp))
//                Column (verticalArrangement = Arrangement.Center){
//                    Text(text = "Podcast 1",
//                        textAlign = TextAlign.Start,
//                        style = TextStyle(
//                            fontWeight = FontWeight.Bold,
//                            fontSize = 16.sp
//                        ),
//                        modifier = Modifier
//                            .wrapContentHeight()
//                            .fillMaxSize())
//                    Spacer(modifier = Modifier.padding(5.dp))
//                    Text(text = "Details / description of each podcast",
//                        textAlign = TextAlign.Justify,
//                        modifier = Modifier
//                            .wrapContentHeight()
//                            .fillMaxSize())
//                }
//            }
//            Spacer(modifier = Modifier.padding(10.dp))
//
//            Row (
//                verticalAlignment = Alignment.CenterVertically
//            ){
//                Image(
//                    painter = painterResource(id = R.drawable.placeholder),
//                    contentDescription = "sample",
//                )
//                Spacer(modifier = Modifier.padding(5.dp))
//                Column (verticalArrangement = Arrangement.Center){
//                    Text(text = "Podcast 2",
//                        textAlign = TextAlign.Start,
//                        style = TextStyle(
//                            fontWeight = FontWeight.Bold,
//                            fontSize = 16.sp
//                        ),
//                        modifier = Modifier
//                            .wrapContentHeight()
//                            .fillMaxSize())
//                    Spacer(modifier = Modifier.padding(5.dp))
//                    Text(text = "Details / description of each podcast",
//                        textAlign = TextAlign.Justify,
//                        modifier = Modifier
//                            .wrapContentHeight()
//                            .fillMaxSize())
//                }
//            }
//
//            Spacer(modifier = Modifier.padding(10.dp))
//
//            Row (
//                verticalAlignment = Alignment.CenterVertically
//            ){
//                Image(
//                    painter = painterResource(id = R.drawable.placeholder),
//                    contentDescription = "sample",
//                )
//                Spacer(modifier = Modifier.padding(5.dp))
//                Column (verticalArrangement = Arrangement.Center){
//                    Text(text = "Podcast 3",
//                        textAlign = TextAlign.Start,
//                        style = TextStyle(
//                            fontWeight = FontWeight.Bold,
//                            fontSize = 16.sp
//                        ),
//                        modifier = Modifier
//                            .wrapContentHeight()
//                            .fillMaxSize())
//                    Spacer(modifier = Modifier.padding(5.dp))
//                    Text(text = "Details / description of each podcast",
//                        textAlign = TextAlign.Justify,
//                        modifier = Modifier
//                            .wrapContentHeight()
//                            .fillMaxSize())
//                }
//            }
//
//            Spacer(modifier = Modifier.padding(10.dp))
//
//            Row (
//                verticalAlignment = Alignment.CenterVertically
//            ){
//                Image(
//                    painter = painterResource(id = R.drawable.placeholder),
//                    contentDescription = "sample",
//                )
//                Spacer(modifier = Modifier.padding(5.dp))
//                Column (verticalArrangement = Arrangement.Center){
//                    Text(text = "Podcast 4",
//                        textAlign = TextAlign.Start,
//                        style = TextStyle(
//                            fontWeight = FontWeight.Bold,
//                            fontSize = 16.sp
//                        ),
//                        modifier = Modifier
//                            .wrapContentHeight()
//                            .fillMaxSize())
//                    Spacer(modifier = Modifier.padding(5.dp))
//                    Text(text = "Details / description of each podcast",
//                        textAlign = TextAlign.Justify,
//                        modifier = Modifier
//                            .wrapContentHeight()
//                            .fillMaxSize())
//                }
//            }
//
//            Spacer(modifier = Modifier.padding(10.dp))
//
//            Row (
//                verticalAlignment = Alignment.CenterVertically
//            ){
//                Image(
//                    painter = painterResource(id = R.drawable.placeholder),
//                    contentDescription = "sample",
//                )
//                Spacer(modifier = Modifier.padding(5.dp))
//                Column (verticalArrangement = Arrangement.Center){
//                    Text(text = "Podcast 5",
//                        textAlign = TextAlign.Start,
//                        style = TextStyle(
//                            fontWeight = FontWeight.Bold,
//                            fontSize = 16.sp
//                        ),
//                        modifier = Modifier
//                            .wrapContentHeight()
//                            .fillMaxSize())
//                    Spacer(modifier = Modifier.padding(5.dp))
//                    Text(text = "Details / description of each podcast",
//                        textAlign = TextAlign.Justify,
//                        modifier = Modifier
//                            .wrapContentHeight()
//                            .fillMaxSize())
//                }
//            }
//
//            Spacer(modifier = Modifier.padding(10.dp))
//
//            Row (
//                verticalAlignment = Alignment.CenterVertically
//            ){
//                Image(
//                    painter = painterResource(id = R.drawable.placeholder),
//                    contentDescription = "sample",
//                )
//                Spacer(modifier = Modifier.padding(5.dp))
//                Column (verticalArrangement = Arrangement.Center){
//                    Text(text = "Podcast 6",
//                        textAlign = TextAlign.Start,
//                        style = TextStyle(
//                            fontWeight = FontWeight.Bold,
//                            fontSize = 16.sp
//                        ),
//                        modifier = Modifier
//                            .wrapContentHeight()
//                            .fillMaxSize())
//                    Spacer(modifier = Modifier.padding(5.dp))
//                    Text(text = "Details / description of each podcast",
//                        textAlign = TextAlign.Justify,
//                        modifier = Modifier
//                            .wrapContentHeight()
//                            .fillMaxSize())
//                }
//            }
//
//            Spacer(modifier = Modifier.padding(10.dp))
//
//            Row (
//                verticalAlignment = Alignment.CenterVertically
//            ){
//                Image(
//                    painter = painterResource(id = R.drawable.placeholder),
//                    contentDescription = "sample",
//                )
//                Spacer(modifier = Modifier.padding(5.dp))
//                Column (verticalArrangement = Arrangement.Center){
//                    Text(text = "Podcast 7",
//                        textAlign = TextAlign.Start,
//                        style = TextStyle(
//                            fontWeight = FontWeight.Bold,
//                            fontSize = 16.sp
//                        ),
//                        modifier = Modifier
//                            .wrapContentHeight()
//                            .fillMaxSize())
//                    Spacer(modifier = Modifier.padding(5.dp))
//                    Text(text = "Details / description of each podcast",
//                        textAlign = TextAlign.Justify,
//                        modifier = Modifier
//                            .wrapContentHeight()
//                            .fillMaxSize())
//                }
//            }
//
//        }
//
//
//        BottomAppBar (
//            modifier = Modifier.fillMaxWidth()
//        ){
//            /* for later */
//        }
//
//    }
//
//}