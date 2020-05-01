package com.nabarunsarkar.safe19india.repository

import com.nabarunsarkar.safe19india.api.DistrictApiService
import com.nabarunsarkar.safe19india.model.DistrictDataDetails
import com.nabarunsarkar.safe19india.utils.State
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.Response

@ExperimentalCoroutinesApi
class DistrictRepository(private val apiService: DistrictApiService) {

    fun getData(): Flow<State<Map<String, DistrictDataDetails>>> {
        return object : NetworkBoundRepository<Map<String, DistrictDataDetails>>() {
            override suspend fun fetchFromRemote(): Response<Map<String, DistrictDataDetails>> = apiService.getData()
        }.asFlow().flowOn(Dispatchers.IO)
    }
}