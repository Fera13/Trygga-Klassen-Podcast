package com.example.tryggaklassenpod.helperFunctions

import android.os.Build
import androidx.annotation.RequiresApi

@RequiresApi(Build.VERSION_CODES.O)
fun main() {

    val password = "aaaaa"

    val (hashedPassword, salt) = PasswordHash.hashPassword(password)

    println("Salt: $salt")
    println("Hashed Password: $hashedPassword")
}