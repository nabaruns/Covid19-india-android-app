package com.nabarunsarkar.safe19india.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class TimeSeriesDetails(
    val dailyconfirmed: String = "0",
    val dailydeceased: String = "0",
    val dailyrecovered: String = "0",
    val date: String = "0",
    val totaldeceased: String = "0",
    val totalrecovered: String = "0",
    val totalconfirmed: String = "0"
)