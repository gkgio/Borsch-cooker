package com.gkgio.domain.support

import io.reactivex.Single

interface SupportService {
    fun loadSupportChatMessages(): Single<List<MessageChat>>
    fun sendSupportChatMessage(text: String): Single<MessageChat>
}