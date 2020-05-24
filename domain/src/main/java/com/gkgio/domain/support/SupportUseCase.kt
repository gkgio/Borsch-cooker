package com.gkgio.domain.support

import io.reactivex.Single
import javax.inject.Inject

interface SupportUseCase {
    fun loadSupportChatMessages(): Single<List<MessageChat>>
    fun sendSupportChatMessage(text: String): Single<MessageChat>
}

class SupportUseCaseImpl @Inject constructor(
    private val supportService: SupportService
) : SupportUseCase {

    override fun loadSupportChatMessages(): Single<List<MessageChat>> =
        supportService.loadSupportChatMessages()

    override fun sendSupportChatMessage(text: String): Single<MessageChat> =
        supportService.sendSupportChatMessage(text)
}