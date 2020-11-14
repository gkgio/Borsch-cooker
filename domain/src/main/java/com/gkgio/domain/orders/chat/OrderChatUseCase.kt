package com.gkgio.domain.orders.chat

import io.reactivex.Single
import javax.inject.Inject

interface OrderChatUseCase {
    fun loadOrderChatMessages(orderId: String): Single<List<MessageChat>>
    fun sendOrderChatMessage(text: String, orderId: String): Single<MessageChat>
}

class OrderChatUseCaseImpl @Inject constructor(
    private val chatService: OrderChatService
) : OrderChatUseCase {

    override fun loadOrderChatMessages(orderId: String): Single<List<MessageChat>> =
        chatService.loadOrderChatMessages(orderId)

    override fun sendOrderChatMessage(text: String, orderId: String): Single<MessageChat> =
        chatService.sendOrderChatMessage(text, orderId)
}