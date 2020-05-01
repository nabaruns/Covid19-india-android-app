package com.nabarunsarkar.safe19india.repository

import com.nabarunsarkar.safe19india.api.AppListAPIService
import com.nabarunsarkar.safe19india.model.AppListResponse
import com.nabarunsarkar.safe19india.utils.State
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.Response

@ExperimentalCoroutinesApi
class AppListRepository(private val apiService: AppListAPIService) {

    fun getData(): Flow<State<AppListResponse>> {
        return object : NetworkBoundRepository<AppListResponse>() {
            override suspend fun fetchFromRemote(): Response<AppListResponse> = apiService.getData()
        }.asFlow().flowOn(Dispatchers.IO)
    }
}