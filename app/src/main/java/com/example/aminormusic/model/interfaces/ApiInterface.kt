package com.example.aminormusic.model.interfaces

import com.example.aminormusic.model.apidataclasses.ApiData
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query


interface ApiInterface {

    @Headers("x-rapidapi-key: RAPID_API_KEY" ,
        "x-rapidapi-host: deezerdevs-deezer.p.rapidapi.com")
    @GET("search")
    fun getData(@Query("q")query: String): retrofit2.Call<ApiData>
}
