package com.nabarunsarkar.safe19india.model


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class FaqResponse(
    @Json(name = "faqs")
    val faqs: List<Faq>
) {
    @JsonClass(generateAdapter = true)
    data class Faq(
        @Json(name = "answer")
        val answer: String,
        @Json(name = "image")
        val image: String,
        @Json(name = "link")
        val link: String,
        @Json(name = "question")
        val question: String
    )
}