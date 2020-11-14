package com.gkgio.data.orders.chat

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MessageChatObjectResponse(
    @Json(name = "message")
    val message: MessageChatResponse
)