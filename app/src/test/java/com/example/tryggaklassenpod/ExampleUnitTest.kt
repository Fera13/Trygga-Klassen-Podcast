package com.example.tryggaklassenpod

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.tryggaklassenpod.helperFunctions.PasswordHash
import org.junit.Test
import org.junit.Assert.*
/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }
}

class PasswordHashTest {
    @Test
    @RequiresApi(Build.VERSION_CODES.O)
    fun testGenerateRandomSalt() {
        val salt = PasswordHash.generateRandomSalt()
        assertEquals(24, salt.length)
    }

    @Test
    @RequiresApi(Build.VERSION_CODES.O)
    fun testHashPassword() {
        val password = "myPassword"
        val (hashedPassword, salt) = PasswordHash.hashPassword(password)

        assertNotEquals(password, hashedPassword)

        assertEquals(24, salt.length)

    }
}