package com.gkgio.borsch_cooker.support.chat

import android.os.Bundle
import android.text.InputType
import android.util.TypedValue
import android.view.View
import com.gkgio.borsch_cooker.base.BaseFragment
import com.gkgio.borsch_cooker.di.AppInjector
import com.gkgio.borsch_cooker.ext.createViewModel
import com.gkgio.borsch_cooker.ext.observeValue
import com.gkgio.borsch_cooker.utils.FragmentArgumentDelegate
import kotlinx.android.synthetic.main.fragment_support_chat.*
import org.joda.time.DateTime
import java.util.*
import com.github.bassaer.chatmessageview.model.Message
import com.gkgio.borsch_cooker.R
import com.gkgio.borsch_cooker.ext.getColorCompat

class SupportChatFragment : BaseFragment<SupportChatViewModel>() {

    companion object {
        fun newInstance(orderId: String, userId: String) = SupportChatFragment().apply {
            this.orderId = orderId
            this.userId = userId
        }
    }

    private var orderId: String by FragmentArgumentDelegate()
    private var userId: String by FragmentArgumentDelegate()

    override fun getLayoutId(): Int = R.layout.fragment_support_chat

    override fun provideViewModel() = createViewModel {
        AppInjector.appComponent.supportChatViewModel
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.init(orderId, userId)
    }

    override fun onResume() {
        super.onResume()
        viewModel.loadMessages()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setChatViewStyle()

        toolbar.setLeftIconClickListener {
            viewModel.onBackClick()
        }

        viewModel.state.observeValue(this) { state ->

            state.chatMessages?.let { loadedMessageList ->
                val listMessages = mutableListOf<Message>()
                val loadedMessageListReversed = loadedMessageList.reversed()
                loadedMessageListReversed.forEach {
                    val message = Message.Builder()
                        .setRight(it.from == userId)
                        .setSendTime(DateTime(it.sentAt).toCalendar(Locale.getDefault()))
                        .setType(Message.Type.TEXT)
                        .setUser(ChatUserUi(if (it.from == userId) "1" else "2", null))
                        .setText(it.text)
                        .build()
                    listMessages.add(message)
                }

                val messageView = chatView.getMessageView()
                messageView.removeAll()
                messageView.init(listMessages)
                messageView.setSelection(messageView.count - 1)
            }
        }

        chatView.setOnClickSendButtonListener(View.OnClickListener {
            val messageText = chatView.inputText
            if (messageText.isNotBlank()) {
                viewModel.onSendMessageClick(messageText)

                val message = Message.Builder()
                    .setRight(true)
                    .setType(Message.Type.TEXT)
                    .setUser(ChatUserUi("1", null))
                    .setText(messageText)
                    .build()

                chatView.send(message)
                chatView.inputText = ""
            }
        })
    }

    private fun setChatViewStyle() {
        chatView.setRightBubbleColor(requireContext().getColorCompat(R.color.blue))
        chatView.setLeftBubbleColor(requireContext().getColorCompat(R.color.grey_bg))
        chatView.setBackgroundColor(requireContext().getColorCompat(R.color.white))
        chatView.setSendButtonColor(requireContext().getColorCompat(R.color.blue))
        chatView.setRightMessageTextColor(requireContext().getColorCompat(R.color.white))
        chatView.setLeftMessageTextColor(requireContext().getColorCompat(R.color.black))
        chatView.setUsernameTextColor(requireContext().getColorCompat(R.color.black))
        chatView.setSendTimeTextColor(requireContext().getColorCompat(R.color.black))
        chatView.setInputTextHint("Новое сообщение")
        chatView.setMessageMarginTop(6)
        chatView.setMessageMarginBottom(6)
        chatView.setMaxInputLine(5)
        chatView.setUsernameFontSize(resources.getDimension(R.dimen.font_small))
        chatView.inputType = InputType.TYPE_TEXT_FLAG_CAP_SENTENCES
        chatView.inputTextColor = requireContext().getColorCompat(R.color.black)
        chatView.setInputTextSize(TypedValue.COMPLEX_UNIT_SP, 20f)
    }

    override fun onPause() {
        super.onPause()
        viewModel.stopLoadMessagesOrder()
    }
}