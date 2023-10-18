package com.example.tryggaklassenpod.navigations

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import com.example.tryggaklassenpod.screens.Screen
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.tryggaklassenpod.screens.AboutScreen
import com.example.tryggaklassenpod.screens.HomeScreen
import com.example.tryggaklassenpod.screens.LoginScreen
import com.example.tryggaklassenpod.screens.AdminScreen
import com.example.tryggaklassenpod.screens.CommentReviewScreen
import com.example.tryggaklassenpod.screens.UploadPodcast
import com.example.tryggaklassenpod.screens.EditPodcasts
import com.example.tryggaklassenpod.screens.PodcastsList
import com.example.tryggaklassenpod.screens.PlayerScreen
import com.example.tryggaklassenpod.screens.CommentReviewScreen
import com.example.tryggaklassenpod.screens.PodcastViewModel



@Composable
fun Navigation() {
    val navController = rememberNavController()

    val podcastViewModel: PodcastViewModel = viewModel()

    NavHost(navController = navController, startDestination = Screen.HomeScreen.route){

        composable(route = Screen.HomeScreen.route){
            HomeScreen(podcastUiState = podcastViewModel.podcastUiState, navController = navController)
        }

        composable(route = Screen.AboutScreen.route){
            AboutScreen(navController = navController)
        }

        composable(route = Screen.AdminScreen.route){
            AdminScreen(navController = navController)
        }

        composable(route = Screen.UploadPodcast.route){
            UploadPodcast(navController = navController)
        }

        composable(route = Screen.PodcastsList.route){
            PodcastsList(navController = navController)
        }

        composable(route = Screen.EditPodcasts.route){
                backStackEntry ->
            val podcastId = backStackEntry.arguments?.getString("podcastId") ?: ""
            EditPodcasts(navController = navController, podcastId = podcastId)
        }
        composable(
            route = "${Screen.EditPodcasts.route}/{podcastId}",
            arguments = listOf(
                navArgument(name = "podcastId") {
                    type = NavType.StringType
                }
            )
        ) { backStackEntry ->
            val podcastId = backStackEntry.arguments?.getString("podcastId")
            EditPodcasts(
                navController = navController,
                podcastId = podcastId ?: ""
            )
        }
        composable(
            route = Screen.CommentReviewScreen.route
        ) { backStackEntry ->
            val episodeId = backStackEntry.arguments?.getString("episodeId")?.toIntOrNull() ?: 0
            CommentReviewScreen(episodeId = episodeId)
            }

        composable(route = Screen.LoginScreen.route) {
            LoginScreen(navController = navController)

        }

        composable(
            route = "${Screen.PlayerScreen.route}/{episodeId}",
            arguments = listOf(
                navArgument(name = "episodeId") {
                    type = NavType.IntType
                }
            )
        ) {index ->
            val episodeId = index.arguments?.getInt("episodeId")
            PlayerScreen(
                episodeId = episodeId,
                viewModel = podcastViewModel,
                goBack = {
                    navController.popBackStack()
                }
            )
        }
    }
}

