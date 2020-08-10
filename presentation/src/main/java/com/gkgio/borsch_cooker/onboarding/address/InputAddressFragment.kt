package com.gkgio.borsch_cooker.onboarding.address

import android.os.Bundle
import android.view.View
import com.gkgio.borsch_cooker.R
import com.gkgio.borsch_cooker.base.BaseFragment
import com.gkgio.borsch_cooker.di.AppInjector
import com.gkgio.borsch_cooker.ext.createViewModel

class InputAddressFragment : BaseFragment<InputAddressViewModel>() {

    override fun getLayoutId(): Int = R.layout.fragment_onboarding

    override fun provideViewModel() = createViewModel {
        AppInjector.appComponent.inputAddressViewModel
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
}