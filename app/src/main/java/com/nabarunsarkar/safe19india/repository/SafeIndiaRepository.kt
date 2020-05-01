package com.nabarunsarkar.safe19india.repository

import com.nabarunsarkar.safe19india.api.Safe19IndiaApiService
import com.nabarunsarkar.safe19india.model.ApiResponse
import com.nabarunsarkar.safe19india.utils.State
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.Response

@ExperimentalCoroutinesApi
class SafeIndiaRepository(private val apiService: Safe19IndiaApiService) {

    fun getData(): Flow<State<ApiResponse>> {
        return object : NetworkBoundRepository<ApiResponse>() {
            override suspend fun fetchFromRemote(): Response<ApiResponse> = apiService.getData()
        }.asFlow().flowOn(Dispatchers.IO)
    }
}