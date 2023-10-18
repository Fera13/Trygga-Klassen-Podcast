package com.example.tryggaklassenpod.sealed

import com.example.tryggaklassenpod.dataClasses.AdminDataClass

sealed class FetchingAdminDataState{
    class Success(val data: MutableList<AdminDataClass>) : FetchingAdminDataState()
    class Failure(val message: String) : FetchingAdminDataState()
    object Loading : FetchingAdminDataState()
    object Empty : FetchingAdminDataState()
}
