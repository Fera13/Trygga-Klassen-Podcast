package com.example.tryggaklassenpod.dataClasses

data class PermissionsDataClass(
    val adminId:String = "",
    val podcastPoster:Boolean = false,
    val podcastEditor:Boolean = false,
    val commentReviewer:Boolean = false
)
