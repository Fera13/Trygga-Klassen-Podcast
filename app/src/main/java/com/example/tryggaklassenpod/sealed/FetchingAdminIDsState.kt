package com.example.tryggaklassenpod.sealed


sealed class FetchingAdminIDsState{
    class Success(val data: MutableList<String>) : FetchingAdminIDsState()
    class Failure(val message: String) : FetchingAdminIDsState()
    object Loading : FetchingAdminIDsState()
    object Empty : FetchingAdminIDsState()
}
