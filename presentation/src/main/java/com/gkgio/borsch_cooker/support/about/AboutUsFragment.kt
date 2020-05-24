package com.gkgio.borsch_cooker.support.about

import android.os.Bundle
import android.view.View
import com.gkgio.borsch_cooker.R
import com.gkgio.borsch_cooker.base.BaseFragment
import com.gkgio.borsch_cooker.di.AppInjector
import com.gkgio.borsch_cooker.ext.createViewModel
import kotlinx.android.synthetic.main.fragment_about_us.*

class AboutUsFragment : BaseFragment<AboutUsViewModel>() {

    override fun getLayoutId(): Int = R.layout.fragment_about_us

    override fun provideViewModel() = createViewModel {
        AppInjector.appComponent.aboutUsViewModel
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        toolbar.setLeftIconClickListener {
            viewModel.onBackClick()
        }
    }
}