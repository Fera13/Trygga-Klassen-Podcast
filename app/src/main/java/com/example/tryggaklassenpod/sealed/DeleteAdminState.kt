package com.example.tryggaklassenpod.sealed

sealed class DeleteAdminState{
    data class Success(val message: String) : DeleteAdminState()
    data class Failure(val error: String) : DeleteAdminState()
}

