package com.example.unittest.data.remote

import com.example.unittest.BuildConfig
import com.example.unittest.data.remote.response.ImageResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface PixabayAPI {

    @GET(value = "/api/")
    suspend fun searchForImage(
        @Query("q") searchQuery : String,
        @Query("key") apiKey : String = BuildConfig.API_KEY
        ) : Response<ImageResponse>


}