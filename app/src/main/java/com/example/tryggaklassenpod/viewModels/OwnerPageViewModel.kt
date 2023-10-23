package com.example.tryggaklassenpod.viewModels

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.tryggaklassenpod.dataClasses.AdminDataClass
import com.example.tryggaklassenpod.sealed.DeleteAdminState
import com.example.tryggaklassenpod.sealed.InsertAdminDataState
import com.example.tryggaklassenpod.sealed.FetchingAdminDataState
import com.example.tryggaklassenpod.sealed.FetchingAdminIDsState
import com.example.tryggaklassenpod.sealed.UpdateAdminState
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class OwnerPageViewModel : ViewModel() {
    private val _message = mutableStateOf<InsertAdminDataState?>(null)
    val message: State<InsertAdminDataState?> = _message

    private val _message2 = mutableStateOf<DeleteAdminState?>(null)
    val deleteMessage: State<DeleteAdminState?> = _message2

    private val _message3 = mutableStateOf<UpdateAdminState?>(null)
    val updateMessage: State<UpdateAdminState?> = _message3

    //private val _message4 = mutableStateOf(mutableListOf<AdminDataClass>())
    //val adminList: State<mutableListOf<String>()> = _message4

    val fetchAdminresponse: MutableState<FetchingAdminDataState> = mutableStateOf(FetchingAdminDataState.Empty)
    val fetchIDresponse: MutableState<FetchingAdminIDsState> = mutableStateOf(FetchingAdminIDsState.Empty)

    init{
        fetchAdmins()
        fetchAdminsIDs()
    }

    private fun fetchAdmins() {
        val tempList = mutableListOf<AdminDataClass>()
        fetchAdminresponse.value = FetchingAdminDataState.Loading
        FirebaseDatabase.getInstance().getReference("admins")
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    for (dataSnap in snapshot.children) {

                        val adminItem = dataSnap.getValue(AdminDataClass::class.java)
                        if (adminItem != null)

                            tempList.add(adminItem)
                    }
                    fetchAdminresponse.value = FetchingAdminDataState.Success(tempList)
                }

                override fun onCancelled(error: DatabaseError) {
                    fetchAdminresponse.value = FetchingAdminDataState.Failure(error.message)
                }

            })
    }

    private fun fetchAdminsIDs() {
        val tempList = mutableListOf<String>()
        fetchIDresponse.value = FetchingAdminIDsState.Loading
        FirebaseDatabase.getInstance().getReference("admins")
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    for (dataSnap in snapshot.children) {
                        val adminID = dataSnap.key
                        if (adminID != null)
                            tempList.add(adminID)
                    }
                    //adminList = tempList
                    fetchIDresponse.value = FetchingAdminIDsState.Success(tempList)
                }

                override fun onCancelled(error: DatabaseError) {
                    fetchIDresponse.value = FetchingAdminIDsState.Failure(error.message)
                }

            })
    }


    fun addNewAdmin(username:String, school:String, password:Map<String, String>, permissions: Map<String, Boolean>) {
        if(username != "" && school != "" && password.isNotEmpty()){
            try {
                // Your data to be inserted
                val admin = AdminDataClass(
                    username = username,
                    password = password,
                    school = school,
                    role = "admin", // Automatically set the role as "admin"
                    permissions = permissions
                )

                val database = FirebaseDatabase.getInstance()
                val adminsRef = database.getReference("admins")

                // Generate a new child location with a unique key
                val newAdminRef = adminsRef.push()

                newAdminRef.setValue(admin).addOnSuccessListener {
                    // Data has been successfully inserted with an automatically generated ID
                    val generatedKey = newAdminRef.key // Get the generated key
                    println("Data has been inserted to admins with ID: $generatedKey")
                    _message.value = InsertAdminDataState.Success("Admin added successfully")
                }.addOnFailureListener { error ->
                    // Handle the error if the data insertion fails
                    println("Error inserting data: $error")
                    _message.value = InsertAdminDataState.Failure("Admin couldn't be added")
                }
            } catch (e: Exception) {
                _message.value = InsertAdminDataState.Failure("An error occurred.")
            }
        } else {
            _message.value = InsertAdminDataState.Failure("Fill all the fields please")
        }

    }

    fun deleteAdminById(id:String){
        try {
            val myRef = FirebaseDatabase.getInstance().getReference("admins")
            myRef.child(id).removeValue().addOnSuccessListener {
                println("Admin deleted")
                _message2.value = DeleteAdminState.Success("Admin deleted successfully")
            }.addOnFailureListener { error ->
                // Handle the error if the data insertion fails
                println("Error deleteing admin: $error")
                _message2.value = DeleteAdminState.Failure("Admin couldn't be deleted")
            }
        } catch (e: Exception) {
            _message2.value = DeleteAdminState.Failure("An error occurred.")
        }
    }

    fun editAdminInfo(adminID:String, username: String, password: Map<String, String>, school: String, permissions: Map<String, Boolean>) {
        try {
            // Your data to be inserted
            val updatedAdmin = mapOf<String, Any>(
                "username" to username,
                "password" to password,
                "school" to school,
                "role" to "admin", // Automatically set the role as "admin"
                "permissions" to permissions
            )

            val myRef = FirebaseDatabase.getInstance().getReference("admins")
            myRef.child(adminID).updateChildren(updatedAdmin).addOnSuccessListener {
                println("Data has been updated for admin with ID: $adminID")
                _message3.value = UpdateAdminState.Success("Admin updated successfully")
            }.addOnFailureListener { error ->
                // Handle the error if the data update fails
                println("Error inserting data: $error")
                _message3.value = UpdateAdminState.Failure("Admin couldn't be updated")
            }
        } catch (e: Exception) {
            _message3.value = UpdateAdminState.Failure("An error occurred.")
        }
    }
}