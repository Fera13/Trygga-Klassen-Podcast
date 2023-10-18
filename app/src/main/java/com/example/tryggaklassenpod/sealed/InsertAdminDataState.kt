package com.example.tryggaklassenpod.sealed

sealed class InsertAdminDataState {
    data class Success(val message: String) : InsertAdminDataState()
    data class Failure(val error: String) : InsertAdminDataState()
}
