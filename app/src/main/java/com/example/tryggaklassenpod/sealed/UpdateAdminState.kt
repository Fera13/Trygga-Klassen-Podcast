package com.example.tryggaklassenpod.sealed

sealed class UpdateAdminState{
    data class Success(val message: String?) : UpdateAdminState()
    data class Failure(val error: String?) : UpdateAdminState()
}
