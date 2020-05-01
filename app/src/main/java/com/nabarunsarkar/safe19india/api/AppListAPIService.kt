package com.nabarunsarkar.safe19india.api

import com.nabarunsarkar.safe19india.model.AppListResponse
import retrofit2.Response
import retrofit2.http.GET

interface AppListAPIService {

    @GET("/nabaruns/nabaruns.github.io/master/img/covid_apps.json")
    suspend fun getData(): Response<AppListResponse>

    companion object {
        const val BASE_URL = "https://raw.githubusercontent.com/"
    }
}