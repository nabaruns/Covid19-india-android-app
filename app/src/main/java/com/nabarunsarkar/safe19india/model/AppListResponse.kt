package com.nabarunsarkar.safe19india.model


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class AppListResponse(
    @Json(name = "apps")
    val apps: List<App>
) {
    @JsonClass(generateAdapter = true)
    data class App(
        @Json(name = "image")
        val image: String,
        @Json(name = "link")
        val link: String,
        @Json(name = "name")
        val name: String,
        @Json(name = "owner")
        val owner: String
    )
}