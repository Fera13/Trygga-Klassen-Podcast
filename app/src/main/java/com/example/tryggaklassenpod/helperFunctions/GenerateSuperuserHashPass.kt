package com.example.tryggaklassenpod.helperFunctions

import android.content.ContentValues.TAG
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi

@RequiresApi(Build.VERSION_CODES.O)
fun generateSuperuserPassword() {
    val password = "superuser"
    val (hashedPassword, salt) = PasswordHash.hashPassword(password)

    Log.e(TAG, "Password: $password")
    Log.e(TAG, "Salt: $salt")
    Log.e(TAG, "Hashed Password: $hashedPassword")
}