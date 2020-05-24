package com.gkgio.borsch_cooker.support

import android.os.Bundle
import android.view.View
import com.gkgio.borsch_cooker.R
import com.gkgio.borsch_cooker.base.BaseFragment
import com.gkgio.borsch_cooker.di.AppInjector
import com.gkgio.borsch_cooker.ext.createViewModel


class SupportFragment : BaseFragment<SupportViewModel>() {

    override fun getLayoutId(): Int = R.layout.fragment_support

    override fun provideViewModel() = createViewModel {
        AppInjector.appComponent.supportViewModel
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}