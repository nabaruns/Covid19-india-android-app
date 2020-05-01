package com.nabarunsarkar.safe19india.model

import com.squareup.moshi.Json

data class TotalDetails(
    val active: String = "0",
    var confirmed: String = "0",
    var deaths: String = "0",
    val recovered: String = "0",
    val statecode: String = "0",

    @Json(name = "lastupdatedtime")
    var lastUpdatedTime: String? = null,
    var state: String = ""
)