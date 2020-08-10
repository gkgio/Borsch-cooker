package com.gkgio.borsch_cooker.onboarding

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import com.gkgio.borsch_cooker.R
import com.gkgio.borsch_cooker.base.BaseFragment
import com.gkgio.borsch_cooker.di.AppInjector
import com.gkgio.borsch_cooker.ext.createViewModel
import com.gkgio.borsch_cooker.ext.observeValue
import com.gkgio.borsch_cooker.ext.setDebounceOnClickListener
import com.gkgio.domain.auth.Cooker
import kotlinx.android.synthetic.main.fragment_onboarding.*


class OnboardingFragment : BaseFragment<OnboardingViewModel>() {

    override fun getLayoutId(): Int = R.layout.fragment_onboarding

    override fun provideViewModel() = createViewModel {
        AppInjector.appComponent.onboardingViewModel
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        actionBtn.setDebounceOnClickListener {
            viewModel.onBtnActionClicked()
        }

        skipBtn.setDebounceOnClickListener {
            viewModel.onBtnSkipClicked()
        }

        viewModel.state.observeValue(viewLifecycleOwner) {
            inputPhoneAnswer.isVisible = it.hasPhone
            inputNameContainer.isVisible = it.hasPhone
            inputNameAnswer.isVisible = it.hasName
            inputAvatarContainer.isVisible = it.hasName
            inputNameView.text = it.nameWithSecondName
            inputPhoneView.text = it.phone

            svOnboardingChat.viewTreeObserver
                .addOnGlobalLayoutListener {
                    if (isAdded) {
                        svOnboardingChat.post { svOnboardingChat.fullScroll(View.FOCUS_DOWN) }
                    }
                }

            createdBtnName(it.cooker, it.avatarInputSkipped)
        }
    }

    private fun createdBtnName(profile: Cooker?, avatarInputSkipped: Boolean) {
        if (profile?.phone.isNullOrBlank()) {
            actionBtn.text = "Подтвердить номер"
            skipBtn.isVisible = false
            return
        }

        if (profile?.firstName.isNullOrBlank()) {
            actionBtn.text = "Указать имя и фамилию"
            skipBtn.isVisible = false
            return
        }

        if (profile?.avatarUrl.isNullOrBlank() && !avatarInputSkipped) {
            actionBtn.text = "Добавить"
            skipBtn.isVisible = true
            return
        }
    }
}