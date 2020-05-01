package com.nabarunsarkar.safe19india.api

import com.nabarunsarkar.safe19india.model.EssentialsResponse
import retrofit2.Response
import retrofit2.http.GET

interface EssentialAPIService {

    @GET("/resources/resources.json")
    suspend fun getData(): Response<EssentialsResponse>

    companion object {
        const val BASE_URL = "https://api.covid19india.org/"
    }
}