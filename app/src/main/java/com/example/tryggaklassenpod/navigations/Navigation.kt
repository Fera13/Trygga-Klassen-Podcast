package com.example.tryggaklassenpod.navigations

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.tryggaklassenpod.languages.abouts.AboutAr
import com.example.tryggaklassenpod.languages.abouts.AboutEng
import com.example.tryggaklassenpod.languages.abouts.AboutEst
import com.example.tryggaklassenpod.languages.abouts.AboutFr
import com.example.tryggaklassenpod.languages.abouts.AboutSp
import com.example.tryggaklassenpod.languages.abouts.AboutSwe
import com.example.tryggaklassenpod.languages.contactus.ContactAr
import com.example.tryggaklassenpod.languages.contactus.ContactEng
import com.example.tryggaklassenpod.languages.contactus.ContactEst
import com.example.tryggaklassenpod.languages.contactus.ContactFr
import com.example.tryggaklassenpod.languages.contactus.ContactSp
import com.example.tryggaklassenpod.languages.contactus.ContactSwe
import com.example.tryggaklassenpod.languages.partnersponser.PartnerSponser
import com.example.tryggaklassenpod.screens.AdminScreen
import com.example.tryggaklassenpod.screens.CommentReviewScreen
import com.example.tryggaklassenpod.screens.EditPodcasts
import com.example.tryggaklassenpod.screens.HomeScreen
import com.example.tryggaklassenpod.screens.LoginScreen
import com.example.tryggaklassenpod.screens.OwnerPageContent
import com.example.tryggaklassenpod.screens.PlayerScreen
import com.example.tryggaklassenpod.viewModels.PodcastViewModel
import com.example.tryggaklassenpod.screens.PodcastsList
import com.example.tryggaklassenpod.screens.Screen
import com.example.tryggaklassenpod.screens.UploadPodcast
import com.example.tryggaklassenpod.screens.viewComments
import com.example.tryggaklassenpod.viewModels.HomeViewModel


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun Navigation(homeViewModel: HomeViewModel = viewModel()) {
    val navController = rememberNavController()
    val podcastViewModel: PodcastViewModel = viewModel()

    NavHost(navController = navController, startDestination = Screen.HomeScreen.route){

        composable(route = Screen.HomeScreen.route){
            HomeScreen(navController = navController, homeViewModel)
        }

        composable(route = Screen.AboutEng.route){
            AboutEng(navController = navController)
        }
        composable(route = Screen.AboutSwe.route){
            AboutSwe(navController = navController)
        }
        composable(route = Screen.AboutFr.route){
            AboutFr(navController = navController)
        }
        composable(route = Screen.AboutSp.route){
            AboutSp(navController = navController)
        }
        composable(route = Screen.AboutEst.route){
            AboutEst(navController = navController)
        }
        composable(route = Screen.AboutAr.route){
            AboutAr(navController = navController)
        }
        composable(route = Screen.ContactEng.route){
            ContactEng(navController = navController)
        }
        composable(route = Screen.ContactSwe.route){
            ContactSwe(navController = navController)
        }
        composable(route = Screen.ContactFr.route){
            ContactFr(navController = navController)
        }
        composable(route = Screen.ContactSpa.route){
            ContactSp(navController = navController)
        }
        composable(route = Screen.ContactEst.route){
            ContactEst(navController = navController)
        }
        composable(route = Screen.ContactAr.route){
            ContactAr(navController = navController)
        }
        composable(route = Screen.PartnerSponser.route){
            PartnerSponser(navController = navController)
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

        composable(route = Screen.CommentReviewScreen.route){
            CommentReviewScreen(navController = navController)
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
            route = "${Screen.ViewComments.route}/{episodeId}",
            arguments = listOf(
                navArgument("episodeId") { type = NavType.IntType }
            )
        ) { backStackEntry ->
            val episodeId = backStackEntry.arguments?.getInt("episodeId")
            if (episodeId != null) {
                viewComments(episodeId)
            }
        }
//            val episodeId = backStackEntry.arguments?.getString("episodeId")?.toIntOrNull() ?: 0
//            CommentReviewScreen(episodeId = episodeId)
//        }


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

        composable(route = Screen.OwnerPage.route){
            OwnerPageContent(navController = navController)
        }
    }
}


