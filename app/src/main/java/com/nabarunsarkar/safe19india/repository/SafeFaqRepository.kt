package com.nabarunsarkar.safe19india.repository

import com.nabarunsarkar.safe19india.api.FaqAPIService
import com.nabarunsarkar.safe19india.model.FaqResponse
import com.nabarunsarkar.safe19india.utils.State
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.Response

@ExperimentalCoroutinesApi
class SafeFaqRepository(private val apiService: FaqAPIService) {

    fun getData(): Flow<State<FaqResponse>> {
        return object : NetworkBoundRepository<FaqResponse>() {
            override suspend fun fetchFromRemote(): Response<FaqResponse> = apiService.getData()
        }.asFlow().flowOn(Dispatchers.IO)
    }
}