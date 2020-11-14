package com.gkgio.domain.orders.chat

import io.reactivex.Single

interface OrderChatService {
    fun loadOrderChatMessages(orderId: String): Single<List<MessageChat>>
    fun sendOrderChatMessage(text: String, orderId: String): Single<MessageChat>
}