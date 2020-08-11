package com.gkgio.borsch_cooker.own

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
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
        ownIsPickupAvailable.setOnCheckedChangeListener { buttonView, isChecked ->
            viewModel.setPickupStatus(isChecked)
        }
        viewModel.state.observeValue(this) {
            if (it.dashboard != null) {
                ownWelcomeTitle.text = getString(R.string.own_welcome, it.dashboard.cookerName)
                ownIsOnDuty.isChecked = it.dashboard.activityStatus
                ownIsDeliveryAvailable.isChecked = it.dashboard.delivery
                ownIsPickupAvailable.isChecked = it.dashboard.pickup
                if (it.dashboard.subscriptionExpirationDate != null) {
                    ownSubscriptionDate.text = it.dashboard.subscriptionExpirationDate
                } else {
                    ownSubscriptionDate.isVisible = false
                }
                if (it.dashboard.activeMeals.isNotEmpty()) {
                    //TODO
                } else {
                    ownActiveMeals.isVisible = false
                }
                if (it.dashboard.reviews.totalReviews != 0) {
                    ownRatingSubtitle.text = it.dashboard.reviews.totalReviews.toString()
                    ownRatingPercentage.text = it.dashboard.reviews.averageRating
                } else {
                    //TODO добавить дизайн, если нет рейтинга
                }
            }
        }
    }
}