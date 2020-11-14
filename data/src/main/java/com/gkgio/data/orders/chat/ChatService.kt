package com.gkgio.data.orders.chat

import com.gkgio.domain.orders.chat.MessageChat
import io.reactivex.Single

interface ChatService {
    fun loadOrderChatMessages(orderId: String): Single<List<MessageChat>>
    fun sendOrderChatMessage(text: String, orderId: String): Single<MessageChat>
}