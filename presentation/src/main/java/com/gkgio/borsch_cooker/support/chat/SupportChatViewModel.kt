package com.gkgio.borsch_cooker.support.chat

import androidx.lifecycle.MutableLiveData
import com.gkgio.borsch_cooker.base.BaseScreensNavigator
import com.gkgio.borsch_cooker.base.BaseViewModel
import com.gkgio.borsch_cooker.ext.applySchedulers
import com.gkgio.borsch_cooker.ext.isNonInitialized
import com.gkgio.borsch_cooker.ext.nonNullValue
import com.gkgio.domain.support.MessageChat
import com.gkgio.domain.support.SupportUseCase
import io.reactivex.Single
import io.reactivex.disposables.Disposable
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class SupportChatViewModel @Inject constructor(
    private val orderChatUseCase: SupportUseCase,
    baseScreensNavigator: BaseScreensNavigator
) : BaseViewModel(baseScreensNavigator) {

    private var disposable: Disposable? = null

    val state = MutableLiveData<State>()

    lateinit var orderId: String
    lateinit var userId: String

    fun init(orderId: String, userId: String) {
        if (state.isNonInitialized()) {
            this.orderId = orderId
            this.userId = userId

            state.value = State()
        }
    }

    fun loadMessages() {
        disposable?.dispose()

        disposable = orderChatUseCase
            .loadSupportChatMessages()
            .repeatWhen { Single.timer(15, TimeUnit.SECONDS).repeat() }
            .applySchedulers()
            .subscribe({
                state.value = state.nonNullValue.copy(chatMessages = it)
            }, {
                processThrowable(it)
            })
    }


    fun onSendMessageClick(text: String) {
        orderChatUseCase
            .sendSupportChatMessage(text)
            .applySchedulers()
            .subscribe({

            }, {
                processThrowable(it)
            })
            .addDisposable()
    }

    fun stopLoadMessagesOrder() {
        disposable?.dispose()
    }

    data class State(
        val chatMessages: List<MessageChat>? = null
    )
}