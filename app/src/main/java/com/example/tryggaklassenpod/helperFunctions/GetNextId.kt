package com.example.tryggaklassenpod.helperFunctions
import android.util.Log
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

fun getNextId(callback: (Int) -> Unit) {
    val databaseReference = FirebaseDatabase.getInstance().getReference("podcast")

    // Query the database to get the last ID
    databaseReference.child("episodes").orderByKey().limitToLast(1)
        .addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                var lastId = 0
                for (childSnapshot in dataSnapshot.children) {
                    lastId = childSnapshot.key?.toInt() ?:0
                    Log.d("LastId", "LastId $lastId")
                }
                val newId = lastId + 1
                callback(newId)
                Log.d("newId", "nextId $newId")
            }


            override fun onCancelled(databaseError: DatabaseError) {
                // Handle the error
            }
        })
}

