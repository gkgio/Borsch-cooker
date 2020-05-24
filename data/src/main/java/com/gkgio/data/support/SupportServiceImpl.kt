package com.gkgio.data.support

import com.gkgio.data.base.BaseService
import com.gkgio.data.exception.ServerExceptionTransformer
import com.gkgio.domain.support.MessageChat
import com.gkgio.domain.support.SupportService
import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Path
import javax.inject.Inject

class SupportServiceImpl @Inject constructor(
    serverExceptionTransformer: ServerExceptionTransformer
) : BaseService(serverExceptionTransformer), SupportService {

    override fun loadSupportChatMessages(): Single<List<MessageChat>> {
        TODO("Not yet implemented")
    }

    override fun sendSupportChatMessage(text: String): Single<MessageChat> {
        TODO("Not yet implemented")
    }

    interface SupportServiceApi {

        @POST("orders/chat/join/{orderId}")
        fun loadSupportChatMessages(@Path("orderId") orderId: String): Single<MessageSupportChatResponse>

        @POST("orders/chat/message/{orderId}")
        fun sendSupportChatMessage(
            @Body chatMessageRequest: MessageChatRequest,
            @Path("orderId") orderId: String
        ): Single<MessageSupportChatResponse>
    }
}