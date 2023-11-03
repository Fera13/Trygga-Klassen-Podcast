package com.example.tryggaklassenpod.internet

import com.example.tryggaklassenpod.dataClasses.Episode
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET


interface ApiService{
    @GET("episodes.json")
    suspend fun getPodcasts(): List<Episode>
}


object RetrofitInstance {

    private const val BASE_URL = "https://trygga-klassen-podcast-default-rtdb.firebaseio.com//podcast/"

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val api: ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }
}

