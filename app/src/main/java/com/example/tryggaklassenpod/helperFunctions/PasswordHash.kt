package com.example.tryggaklassenpod.helperFunctions

import android.content.ContentValues.TAG
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import java.security.MessageDigest
import java.security.SecureRandom
import java.util.Base64

object PasswordHash {
    private val random = SecureRandom()

    @RequiresApi(Build.VERSION_CODES.O)
    fun generateRandomSalt(): String {
        val salt = ByteArray(16)
        random.nextBytes(salt)
        return Base64.getEncoder().encodeToString(salt)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun hashPassword(password: String): Pair<String, String> {
        val salt = generateRandomSalt()
        val data = password + salt
        val digest = MessageDigest.getInstance("SHA-256")
        val bytes = digest.digest(data.toByteArray())

        val hashedPassword = StringBuilder()
        for (byte in bytes) {
            hashedPassword.append(String.format("%02x", byte))
        }

        return Pair(hashedPassword.toString(), salt)
    }

    fun hashAndComparePassword(inputPassword: String, storedSalt: String, storedHashedPassword: String): Boolean {
        val data = inputPassword + storedSalt
        val digest = MessageDigest.getInstance("SHA-256")
        val bytes = digest.digest(data.toByteArray())

        val hashedPassword = StringBuilder()
        for (byte in bytes) {
            hashedPassword.append(String.format("%02x", byte))
        }

        Log.i(TAG, "Input Password Hashed: $hashedPassword")
        Log.i(TAG, "Stored Hashed Password: $storedHashedPassword")

        return hashedPassword.toString() == storedHashedPassword
    }
}

