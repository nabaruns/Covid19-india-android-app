package com.nabarunsarkar.safe19india.model

import com.squareup.moshi.Json

data class ApiResponse(
    @Json(name = "statewise")
    val stateWiseDetails: List<TotalDetails>,
    @Json(name = "cases_time_series")
    val casesTimeSeries: List<TimeSeriesDetails>
)