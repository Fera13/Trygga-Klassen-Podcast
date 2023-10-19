package com.example.tryggaklassenpod.screens

import android.content.ContentValues
import android.content.ContentValues.TAG
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.tryggaklassenpod.R
import com.example.tryggaklassenpod.dataClasses.AdminDataClass
import com.example.tryggaklassenpod.dataClasses.LoginDataClass
import com.example.tryggaklassenpod.helperFunctions.PasswordHash
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(navController: NavController) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var showError by remember { mutableStateOf(false) }
    var userRole by remember { mutableStateOf<String?>(null) }
    var loggedIn by remember { mutableStateOf(false) }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color.White
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.tryggaklassen_podden),
                contentDescription = "Logo",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .padding(top = 20.dp, bottom = 20.dp)
            )

            OutlinedTextField(
                value = username,
                onValueChange = { newUsername ->
                    username = newUsername
                    userRole = null
                    loggedIn = false
                },
                label = { Text(text = "Username") },
                textStyle = TextStyle(color = Color.Black),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp, 0.dp, 8.dp, 16.dp),
            )

            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text(text = "Password") },
                textStyle = TextStyle(color = Color.Black),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Done
                ),
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
            )

            Spacer(modifier = Modifier.height(16.dp))

            if (showError) {
                Text(
                    text = "The username or password is invalid. Please try again.",
                    color = Color.Red,
                    modifier = Modifier.padding(8.dp)
                )
            }

            Button(
                onClick = {
                    authenticateUser(username, password) { isAuthenticated, role ->
                        if (isAuthenticated) {
                            userRole = role
                            loggedIn = true

                            if (role == "admin") {
                                // Navigate to the admin screen right here
                                navController.navigate(Screen.AdminScreen.route)
                            }
                        } else {
                            showError = true
                        }
                    }
                },
                modifier = Modifier.padding(8.dp),
            ) {
                Text(text = "Login")
            }

            if (loggedIn) {
                Text(
                    text = "Logged in as: $username\nRole: $userRole",
                    modifier = Modifier.padding(8.dp)
                )
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
private fun authenticateUser(username: String, password: String, onAuthenticationResult: (Boolean, String?) -> Unit) {
    val database: DatabaseReference = FirebaseDatabase.getInstance().getReference("admins")

    Log.i(TAG, "Before database query")
    database.orderByChild("username").equalTo(username).addListenerForSingleValueEvent(object : ValueEventListener {
        override fun onDataChange(snapshot: DataSnapshot) {
            Log.i(TAG, "Query successful")
            if (snapshot.exists()) {
                Log.i(TAG, "Snapshot exist")
                for (childSnapshot in snapshot.children) {
                    val adminData = childSnapshot.getValue(LoginDataClass::class.java)
                    Log.i(TAG, "Fetched adminData: $adminData")
                    if (adminData != null) {
                        val passwordMap = adminData.password
                        val hashedPassword = passwordMap?.get("hashedPassword")
                        val salt = passwordMap?.get("salt")
                        Log.i(TAG, "Authentication successful")
                        onAuthenticationResult(true, adminData.role)
                        return  // Found a match, exit the loop
                    }
                }
            }
            // No match found for the provided username and password
            onAuthenticationResult(false, null)
        }

        override fun onCancelled(error: DatabaseError) {
            Log.e(TAG, "Database query cancelled: ${error.message}")
            onAuthenticationResult(false, null)
        }
    })
}

