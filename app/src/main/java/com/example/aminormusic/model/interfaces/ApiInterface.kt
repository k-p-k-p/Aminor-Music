package com.example.aminormusic.model.interfaces

import com.example.aminormusic.model.apidataclasses.ApiData
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query


interface ApiInterface {

    @Headers("x-rapidapi-key: 3d6178deb1mshf75399de754f8d6p1767fejsn859830a9af27" , "x-rapidapi-host: deezerdevs-deezer.p.rapidapi.com")
    @GET("search")
    fun getData(@Query("q")query: String): retrofit2.Call<ApiData>
}