package com.nabarunsarkar.safe19india.api

import com.nabarunsarkar.safe19india.model.ApiResponse
import retrofit2.Response
import retrofit2.http.GET

interface Safe19IndiaApiService {

    @GET("/data.json")
    suspend fun getData(): Response<ApiResponse>

    companion object {
        const val BASE_URL = "https://api.covid19india.org/"
    }
}
