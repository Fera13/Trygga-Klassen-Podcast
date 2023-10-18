package com.example.tryggaklassenpod.dataClasses

data class Comments(
    var commentId: Int? = 0,
    val comment: String? = "",
    val author: String? = "",

    var approved: Boolean? = false,
    val createdAt: Long? = 0,
    val likes: Int? = 0
)
