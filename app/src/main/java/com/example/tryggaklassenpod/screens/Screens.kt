package com.example.tryggaklassenpod.screens


sealed class Screen(val route: String){
    object HomeScreen: Screen("homeScreen")
    object AboutScreen: Screen("aboutScreen")
    object LoginScreen: Screen("LoginScreen")
    object AdminScreen: Screen("adminScreen")
    object UploadPodcast: Screen("uploadPodcast")
    object PodcastsList: Screen("podcastsList")
    object EditPodcasts: Screen("editPodcasts")
    object CommentReviewScreen: Screen("CommentReviewScreen")
    object PlayerScreen: Screen("playerScreen")
    object OwnerPage: Screen("OwnerPage")
    object ViewComments: Screen("ViewComments")


}