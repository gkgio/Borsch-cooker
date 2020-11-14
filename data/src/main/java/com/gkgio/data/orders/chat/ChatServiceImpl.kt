package com.gkgio.data.orders.chat

import com.gkgio.data.base.BaseService
import com.gkgio.data.exception.ServerExceptionTransformer
import com.gkgio.domain.orders.chat.MessageChat
import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Path
import javax.inject.Inject

class ChatServiceImpl @Inject constructor(
    private val chatServiceApi: ChatServiceApi,
    private val messagesChatResponseTransformer: MessagesChatResponseTransformer,
    serverExceptionTransformer: ServerExceptionTransformer
) : BaseService(serverExceptionTransformer), ChatService {


    override fun loadOrderChatMessages(orderId: String): Single<List<MessageChat>> = executeRequest(
        chatServiceApi.loadOrderChatMessages(orderId).map { orderChatResponse ->
            if (orderChatResponse.messageList == null) {
                listOf()
            } else {
                orderChatResponse.messageList.map {
                    messagesChatResponseTransformer.transform(it)
                }
            }
        }
    )

    override fun sendOrderChatMessage(text: String, orderId: String): Single<MessageChat> =
        executeRequest(
            chatServiceApi.sendOrderChatMessage(MessageChatRequest(text), orderId)
                .map { messagesChatResponseTransformer.transform(it.message) }
        )

    interface ChatServiceApi {
        @POST("orders/chat/join/{orderId}")
        fun loadOrderChatMessages(@Path("orderId") orderId: String): Single<OrderChatResponse>

        @POST("orders/chat/message/{orderId}")
        fun sendOrderChatMessage(
            @Body chatMessageRequest: MessageChatRequest,
            @Path("orderId") orderId: String
        ): Single<MessageChatObjectResponse>
    }

}