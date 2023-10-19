package com.example.tryggaklassenpod.dataClasses

data class LoginDataClass (
    val username: String? = null,
    val password: Map<String, String>? = null,
    val role: String = "admin",
)