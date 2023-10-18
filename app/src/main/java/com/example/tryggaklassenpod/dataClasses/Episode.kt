
package com.example.tryggaklassenpod.dataClasses

data class Episode(
    var id: Int = 0,
    val imageUrl: String = "",
    val episodeUrl: String = "",
    val duration: Int = 0,

    val title: String = "",
    val description: String = "",
    val comments: List<Comments>? = emptyList(),
    val categories: List<String>? = emptyList()
)

