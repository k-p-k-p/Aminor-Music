package com.example.aminormusic.model.apicallerclasses

import com.example.aminormusic.model.apidataclasses.ApiData
import com.example.aminormusic.model.interfaces.ApiInterface
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class FetchData {
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://deezerdevs-deezer.p.rapidapi.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val service = retrofit.create(ApiInterface::class.java)

    fun fetchData(query: String = "eminem", callback: (ApiData?) -> Unit) {
        service.getData(query).enqueue(object : retrofit2.Callback<ApiData?> {
            override fun onResponse(call: retrofit2.Call<ApiData?>, response: retrofit2.Response<ApiData?>) {
                if (response.isSuccessful) {
                    callback(response.body())
                } else {
                    callback(null)
                }
            }

            override fun onFailure(call: retrofit2.Call<ApiData?>, t: Throwable) {
                callback(null)
            }
        })
    }
}
