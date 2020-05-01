package com.nabarunsarkar.safe19india.api

import com.nabarunsarkar.safe19india.model.FaqResponse
import retrofit2.Response
import retrofit2.http.GET

interface FaqAPIService {

    @GET("/nabaruns/nabaruns.github.io/master/img/covid_faq.json")
    suspend fun getData(): Response<FaqResponse>

    companion object {
        const val BASE_URL = "https://raw.githubusercontent.com/"
    }
}