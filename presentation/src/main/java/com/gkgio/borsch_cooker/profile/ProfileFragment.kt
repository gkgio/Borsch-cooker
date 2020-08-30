package com.gkgio.borsch_cooker.profile

import android.os.Bundle
import android.view.View
import com.gkgio.borsch_cooker.R
import com.gkgio.borsch_cooker.base.BaseFragment
import com.gkgio.borsch_cooker.di.AppInjector
import com.gkgio.borsch_cooker.ext.createViewModel


class ProfileFragment : BaseFragment<ProfileViewModel>() {

    override fun getLayoutId(): Int = R.layout.fragment_profile

    override fun provideViewModel() = createViewModel {
        AppInjector.appComponent.profileViewModel
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}