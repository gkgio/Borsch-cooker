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
        ownIsOnDuty.setDebounceOnClickListener {
            viewModel.setDutyStatus(ownIsOnDuty.isChecked)
        }

        ownIsDeliveryAvailable.setDebounceOnClickListener {
            viewModel.setDeliveryStatus(ownIsDeliveryAvailable.isChecked)
        }

        ownIsPickupAvailable.setDebounceOnClickListener {
            viewModel.setPickupStatus(ownIsPickupAvailable.isChecked)
        }

        profileContainer.setDebounceOnClickListener {
            viewModel.onProfileClicked()
        }

        viewModel.state.observeValue(this) { state ->
            state.dashboard?.let { dashboard ->
                with(dashboard) {
                    ownWelcomeTitle.text = getString(R.string.own_welcome, cookerName)
                    ownIsOnDuty.isChecked = activityStatus
                    ownIsDeliveryAvailable.isChecked = delivery
                    ownIsPickupAvailable.isChecked = pickup

                    ownSubscriptionDate.setTextOrHide(subscriptionExpirationDate)

                    if (activeMeals.isNotEmpty()) {
                        //TODO
                    } else {
                        ownActiveMeals.isVisible = false
                    }
                    if (reviews.totalReviews != 0) {
                        ownRatingSubtitle.text = reviews.totalReviews.toString()
                        ownRatingPercentage.text = reviews.averageRating
                    } else {
                        //TODO добавить дизайн, если нет рейтинга
                    }
                }
            }
        }
    }
}