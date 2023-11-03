package com.example.tryggaklassenpod.screens

import android.annotation.SuppressLint
import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.tryggaklassenpod.dataClasses.Episode
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.example.tryggaklassenpod.veiwModel.HomeViewModel
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.example.tryggaklassenpod.R
import com.example.tryggaklassenpod.externalResources.HighPriorityPodcast
import com.example.tryggaklassenpod.externalResources.MixcloudEmbedActivity

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HomeScreen(navController: NavController, homeViewModel: HomeViewModel) {

    val episodes by homeViewModel.episodeLiveData.observeAsState(emptyList())
    val isRefreshing by homeViewModel.isRefreshing.observeAsState(false)

    val onRefresh: () -> Unit = {
        homeViewModel.refreshing()
    }

    var menuExpanded by remember { mutableStateOf(false) }
    val myContext = LocalContext.current

    if (!episodes.isEmpty()) {
        SwipeRefresh(
            state = rememberSwipeRefreshState(isRefreshing),
            onRefresh = { onRefresh() }
        ) {
            MaterialTheme {
                Scaffold(
                    containerColor  = Color(0xFF006971),
                    topBar = {
                        TopAppBar(
                            title = {
                                Text(
                                    text = "Trygga Klassen",
                                    textAlign = TextAlign.Center,
                                    modifier = Modifier.fillMaxWidth(),
                                    style = TextStyle(
                                        color = Color(0xFF00363B),
                                        fontFamily = FontFamily.Serif,
                                        fontSize = 25.sp,
                                    )
                                )
                            },
                            actions = {
                                IconButton(onClick = { menuExpanded = !menuExpanded }) {
                                    Icon(Icons.Filled.MoreVert, contentDescription = "")
                                }
                                DropdownMenu(
                                    expanded = menuExpanded,
                                    onDismissRequest = { menuExpanded = false }) {

                                    DropdownMenuItem(
                                        text = { Text(text = "Login") },
                                        onClick = { navController.navigate(Screen.LoginScreen.route) }  //login page will be added
                                    )
                                    DropdownMenuItem(
                                        text = { Text(text = "About us") },
                                        onClick = { navController.navigate(Screen.AboutEng.route) }
                                    )

                                    DropdownMenuItem(
                                        text = { Text(text = "Contact us") },
                                        onClick = { navController.navigate(Screen.ContactEng.route) }
                                    )

                                    DropdownMenuItem(
                                        text = { Text(text = "Partners/Sponsers") },
                                        onClick = { navController.navigate(Screen.PartnerSponser.route) }
                                    )

                                }
                            }

                        )
                    },
                    content = {
                        Box (
                            modifier = Modifier
                                .padding(it)
                                .background(Color(0xFF006971))
                        ){
                            Column(
                                verticalArrangement = Arrangement.Center,
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                HighPriorityPodcast()
//                                // Load the sample image using the painterResource function
//                                val imagePainter: Painter =
//                                    painterResource(id = R.drawable.sample_audio)
//
//                                Image(
//                                    painter = imagePainter,
//                                    contentDescription = "Sample Image",
//                                    modifier = Modifier
//                                        .fillMaxWidth(0.9f)
//                                        .clickable {
//                                            // Launch the MixcloudEmbedActivity
//                                            val intent =
//                                                Intent(myContext, MixcloudEmbedActivity::class.java)
//                                            myContext.startActivity(intent)
//                                        }
//                                )

                                showEpisodesList(episodes, navController)
                            }

                        }

                    }
                )
            }

        }
    } else {
        SwipeRefresh(
            state = rememberSwipeRefreshState(isRefreshing),
            onRefresh = { onRefresh() }
        ){
            MaterialTheme {
                Scaffold(
                    containerColor  = Color(0xFF006971),
                    topBar = {
                        TopAppBar(
                            title = {
                                Text(
                                    text = "Trygga Klassen",
                                    textAlign = TextAlign.Center,
                                    modifier = Modifier.fillMaxWidth(),
                                    style = TextStyle(
                                        color = Color(0xFF00363B),
                                        fontFamily = FontFamily.Serif,
                                        fontSize = 25.sp,
                                    )
                                )
                            },
                            actions = {
                                IconButton(onClick = { menuExpanded = !menuExpanded }) {
                                    Icon(Icons.Filled.MoreVert, contentDescription = "")
                                }
                                DropdownMenu(
                                    expanded = menuExpanded,
                                    onDismissRequest = { menuExpanded = false }) {

                                    DropdownMenuItem(
                                        text = { Text(text = "Login") },
                                        onClick = { navController.navigate(Screen.LoginScreen.route) }  //login page will be added
                                    )
                                    DropdownMenuItem(
                                        text = { Text(text = "About us") },
                                        onClick = {navController.navigate(Screen.AboutEng.route)},
                                    )
                                    DropdownMenuItem(
                                        text = { Text(text = "Contact us") },
                                        onClick = { navController.navigate(Screen.ContactEng.route) }
                                    )

                                    DropdownMenuItem(
                                        text = { Text(text = "Partners/Sponsers") },
                                        onClick = { navController.navigate(Screen.PartnerSponser.route) }
                                    )


                                }
                            }

                        )
                    },
                    content = {
                        Box(Modifier.padding(it)
                            .background(Color(0xFF006971))
                            ){
                            Column(
                                modifier = Modifier.padding(10.dp),
                                verticalArrangement = Arrangement.Center,
                                horizontalAlignment = Alignment.CenterHorizontally,
                            ) {
                                Spacer(modifier = Modifier.padding(20.dp))
                                Text(text = "Please check your Internet connection...",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 20.sp,
                                    color = Color.White
                                )
                            }
                        }

                    }
                )
            }
        }
    }

}



@Composable
fun showEpisodesList(episodes: List<Episode>, navController: NavController){
    LazyColumn (modifier = Modifier.padding(10.dp)){
        items(episodes){episode ->
            if (episode != null){
                EpisodeListItem(episode, navController)

            }
        }
    }
}


@Composable
fun EpisodeListItem(episode: Episode, navController: NavController){
    Column (
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
           ,
        horizontalAlignment = Alignment.CenterHorizontally
    ){

        AsyncImage(
            model = episode.imageUrl,
            contentDescription = "thumbnail",
            modifier = Modifier.clickable { navController.navigate("${Screen.PlayerScreen.route}/${episode.id}") })
        Text(text = episode.title,
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
        )
        Text(
            text = episode.description,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )

    }
}


@Preview(showBackground = true)
@Composable
fun HomeScreenPreview(){
    HomeScreen(rememberNavController(), HomeViewModel() )
}