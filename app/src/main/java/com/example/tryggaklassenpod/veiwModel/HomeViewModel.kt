package com.example.tryggaklassenpod.veiwModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tryggaklassenpod.dataClasses.Episode
import com.example.tryggaklassenpod.internet.ApiService
import com.example.tryggaklassenpod.internet.RetrofitInstance
import kotlinx.coroutines.launch

class HomeViewModel: ViewModel() {
    private val apiService: ApiService = RetrofitInstance.api

    private val _episodesLiveData = MutableLiveData<List<Episode>>()
    val episodeLiveData: LiveData<List<Episode>> get() =_episodesLiveData

    private val _isRefreshing = MutableLiveData<Boolean>()
    val isRefreshing: LiveData<Boolean> get() =_isRefreshing

    init {
        fetchDataFromDB()
    }

    fun refreshing(){
        fetchDataFromDB()
    }

    private fun fetchDataFromDB(){
        viewModelScope.launch {
            _isRefreshing.postValue(true)

            try {
                val dataList = apiService.getPodcasts()
                _episodesLiveData.postValue(dataList)
            } catch (e: Exception){
                _episodesLiveData.postValue(emptyList())
            } finally {
                _isRefreshing.postValue(false)
            }
        }
    }

}