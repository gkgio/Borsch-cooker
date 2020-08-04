package com.gkgio.borsch_cooker.own

import android.os.Bundle
import android.view.View
import com.gkgio.borsch_cooker.R
import com.gkgio.borsch_cooker.base.BaseFragment
import com.gkgio.borsch_cooker.di.AppInjector
import com.gkgio.borsch_cooker.ext.*
import kotlinx.android.synthetic.main.fragment_own.*


class OwnFragment : BaseFragment<OwnViewModel>() {

    companion object {
        val TAG = OwnFragment::class.java.simpleName
    }

    override fun getLayoutId(): Int = R.layout.fragment_own

    override fun provideViewModel() = createViewModel {
        AppInjector.appComponent.ownViewModel
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        ownIsOnDuty.setOnCheckedChangeListener { buttonView, isChecked ->
            viewModel.setDutyStatus(isChecked)
        }
        ownIsDeliveryAvailable.setOnCheckedChangeListener { buttonView, isChecked ->
            viewModel.setDeliveryStatus(isChecked)
        }
    }
}