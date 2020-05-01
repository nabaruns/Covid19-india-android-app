package com.nabarunsarkar.safe19india.model


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class EssentialsResponse(
    @Json(name = "resources")
    val resources: List<Resource>
) {
    @JsonClass(generateAdapter = true)
    data class Resource(
        @Json(name = "category")
        val category: String,
        @Json(name = "city")
        val city: String,
        @Json(name = "contact")
        val contact: String,
        @Json(name = "descriptionandorserviceprovided")
        val descriptionandorserviceprovided: String,
        @Json(name = "nameoftheorganisation")
        val nameoftheorganisation: String,
        @Json(name = "phonenumber")
        val phonenumber: String,
        @Json(name = "state")
        val state: String
    )
}