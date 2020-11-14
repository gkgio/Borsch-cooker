package com.gkgio.data.orders.chat

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MessageChatRequest(
    @Json(name = "text")
    val text: String
)