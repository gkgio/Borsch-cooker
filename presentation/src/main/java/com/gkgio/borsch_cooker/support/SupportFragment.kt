package com.gkgio.borsch_cooker.support

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import com.gkgio.borsch_cooker.R
import com.gkgio.borsch_cooker.base.BaseFragment
import com.gkgio.borsch_cooker.di.AppInjector
import com.gkgio.borsch_cooker.ext.createViewModel
import com.gkgio.borsch_cooker.ext.observeValue
import com.gkgio.borsch_cooker.ext.openLink
import com.gkgio.borsch_cooker.ext.setDebounceOnClickListener
import kotlinx.android.synthetic.main.fragment_support.*


class SupportFragment : BaseFragment<SupportViewModel>() {

    override fun getLayoutId(): Int = R.layout.fragment_support

    override fun provideViewModel() = createViewModel {
        AppInjector.appComponent.supportViewModel
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        openFaq.setDebounceOnClickListener {
            viewModel.onFaqClick()
        }
        openChat.setDebounceOnClickListener {
            viewModel.onChatClick()
        }
        viewModel.openFaq.observeValue(this) {
            requireContext().openLink(getString(R.string.support_fragment_faq_url))
        }
        viewModel.openChat.observeValue(this) {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.support_fragment_telegram_link)))
            startActivity(intent)
        }
        viewModel.chatNeedApp.observeValue(this) {
            showError(getString(R.string.support_fragment_telegram_need_app))
        }
    }
}