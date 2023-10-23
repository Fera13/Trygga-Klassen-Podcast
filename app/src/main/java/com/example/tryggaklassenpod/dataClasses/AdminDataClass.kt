package com.example.tryggaklassenpod.dataClasses

data class AdminDataClass(
val username: String? = null,
val password: Map<String, String>? = null,
val school: String? = null,
val role: String = "admin",
val permissions: Map<String, Boolean>? = null,
)
