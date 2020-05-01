package com.nabarunsarkar.safe19india.repository

import com.nabarunsarkar.safe19india.api.EssentialAPIService
import com.nabarunsarkar.safe19india.model.EssentialsResponse
import com.nabarunsarkar.safe19india.utils.State
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.Response

@ExperimentalCoroutinesApi
class EssentialRepository(private val apiService: EssentialAPIService) {

    fun getData(): Flow<State<EssentialsResponse>> {
        return object : NetworkBoundRepository<EssentialsResponse>() {
            override suspend fun fetchFromRemote(): Response<EssentialsResponse> = apiService.getData()
        }.asFlow().flowOn(Dispatchers.IO)
    }
}