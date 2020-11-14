package com.gkgio.data.orders.chat

import com.gkgio.data.BaseTransformer
import com.gkgio.domain.orders.chat.MessageChat
import javax.inject.Inject

class MessagesChatResponseTransformer @Inject constructor() :
    BaseTransformer<MessageChatResponse, MessageChat> {

    override fun transform(data: MessageChatResponse) = with(data) {
        MessageChat(
            from,
            fromSupport,
            icon,
            id,
            orderId,
            sentAt,
            system,
            systemStatus,
            text,
            timestamp,
            title,
            to,
            toSupport
        )
    }
}