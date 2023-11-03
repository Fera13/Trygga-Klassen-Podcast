package com.example.tryggaklassenpod.helperFunctions

import com.google.firebase.auth.FirebaseAuth

class Authentication  {
    fun signInWithEmailAndPassword(email: String, password: String, callback: (Boolean) -> Unit) {
        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // User is signed in, notify success
                    callback(true)
                } else {
                    // Sign-in failed, notify failure
                    callback(false)
                }
            }
    }
}
