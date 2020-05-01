package com.nabarunsarkar.safe19india.api

import com.nabarunsarkar.safe19india.model.DistrictDataDetails
import retrofit2.Response
import retrofit2.http.GET

interface DistrictApiService {

    @GET("/state_district_wise.json")
    suspend fun getData(): Response<Map<String, DistrictDataDetails>>

    companion object {
        const val BASE_URL = "https://api.covid19india.org/"
    }
}