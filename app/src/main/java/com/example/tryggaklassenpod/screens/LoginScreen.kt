package com.example.tryggaklassenpod.screens

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
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.tryggaklassenpod.R
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
    var passwordVisibility by remember { mutableStateOf(false) }


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
                    showError = false
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
                onValueChange = {
                    password = it
                    showError = false
                },
                label = { Text(text = "Password") },
                textStyle = TextStyle(color = Color.Black),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Done
                ),
                visualTransformation = if (passwordVisibility) VisualTransformation.None else PasswordVisualTransformation(),
                trailingIcon = {
                    val image = if (passwordVisibility) {
                        painterResource(id = R.drawable.eye_open)
                    } else {
                        painterResource(id = R.drawable.eye_closed)
                    }

                    IconButton(
                        onClick = { passwordVisibility = !passwordVisibility }
                    ) {
                        Icon(
                            painter = image,
                            contentDescription = if (passwordVisibility) "Hide password" else "Show password",
                        )
                    }
                },
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
                    authenticateUser(username, password, navController) { isAuthenticated, role ->
                        if (isAuthenticated) {
                            when (role) {
                                "admin" -> {
                                    // Route to the AdminScreen for admin
                                    navController.navigate("AdminScreen")
                                }
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
        }

    }
}


private fun authenticateUser(username: String, password: String,navController: NavController, onAuthenticationResult: (Boolean, String?) -> Unit) {
    val database: DatabaseReference = FirebaseDatabase.getInstance().getReference("admins")

    Log.d(TAG, "Before database query")
    database.orderByChild("username").equalTo(username).addListenerForSingleValueEvent(object : ValueEventListener {
        override fun onDataChange(snapshot: DataSnapshot) {
            Log.d(TAG, "Query successful")
            if (snapshot.exists()) {
                Log.d(TAG, "Snapshot exist")
                var isAuthenticated = false
                var userRole: String? = null

                for (childSnapshot in snapshot.children) {
                    val adminData = childSnapshot.getValue(LoginDataClass::class.java)
                    Log.d(TAG, "Fetched adminData: $adminData")
                    if (adminData != null) {
                        val passwordMap = adminData.password
                        val hashedPassword = passwordMap?.get("hashedPass")
                        val salt = passwordMap?.get("salt")
                        if (hashedPassword != null && salt != null) {
                            if (PasswordHash.hashAndComparePassword(password, salt, hashedPassword)) {
                                Log.d(TAG, "Password is correct")
                                isAuthenticated = true
                                userRole = adminData.role
                                break // Exit the loop once a valid user is found
                            } else {
                                Log.e(TAG, "Password is Incorrect")
                            }
                        } else {
                            Log.e(TAG, "Password or salt is not found")
                        }
                    } else {
                        Log.e(TAG, "adminData is null")
                    }
                }

                if (isAuthenticated) {
                    onAuthenticationResult(true, userRole)
                } else {
                    onAuthenticationResult(false, null)
                }

            } else if (!snapshot.exists()) {
                // Check if the user is the "superuser"
                Log.d(TAG, "Checking for superuser")
                checkForSuperuser(username, password) { isSuperuser, role ->
                    if (isSuperuser) {
                        Log.d(TAG, "SuperUser found")
                        when (role) {
                            "superuser" -> {
                                // Route to the OwnerScreen for superuser
                                navController.navigate("OwnerPage")
                            }
                        }
                        onAuthenticationResult(true, "superuser")
                    } else {
                        onAuthenticationResult(false, null)
                    }
                }
            }
        }

        override fun onCancelled(error: DatabaseError) {
            Log.d(TAG, "Database query cancelled: ${error.message}")
            onAuthenticationResult(false, null)
        }
    })
}

private fun checkForSuperuser(username: String, password: String, onResult: (Boolean, String?) -> Unit) {
    val superuserDatabase: DatabaseReference = Firebase.database.reference.child("superusers")
    Log.d(TAG, "database path: $superuserDatabase")
    Log.d(TAG, "Checking superuser database for username: $username")
    superuserDatabase.child("id1").get().addOnSuccessListener { dataSnapshot ->
        val userData = dataSnapshot.value as? Map<String, Any>
        Log.d(TAG, "Data retrieved for username: $userData")

        if (userData != null && userData["password"] == password) {
            val role = userData["role"] as? String
            Log.d(TAG, "Authentication successful for username: $username")
            onResult(true, role)
        } else {
            Log.e(TAG, "Authentication failed for username: $username")
            onResult(false, null)
        }
    }.addOnFailureListener { exception ->
        Log.e(TAG, "Error when checking for superuser: $exception")
        onResult(false, null)
    }
}


