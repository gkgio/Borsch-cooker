package com.gkgio.borsch_cooker.own

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.gkgio.borsch_cooker.R
import com.gkgio.borsch_cooker.base.BaseFragment
import com.gkgio.borsch_cooker.di.AppInjector
import com.gkgio.borsch_cooker.ext.*
import com.gkgio.borsch_cooker.orders.OrdersMealsAdapter
import kotlinx.android.synthetic.main.fragment_own.*


class OwnFragment : BaseFragment<OwnViewModel>() {

    private lateinit var activeMealsAdapter: OrdersMealsAdapter

    companion object {
        val TAG = OwnFragment::class.java.simpleName
        const val BUTTON_HELP_STATUS = "helpStatus"
        const val BUTTON_HELP_DELIVERY = "helpDelivery"
        const val BUTTON_BUY_CONTAINERS = "buyContainers"
    }

    override fun getLayoutId(): Int = R.layout.fragment_own

    override fun provideViewModel() = createViewModel {
        AppInjector.appComponent.ownViewModel
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initMealsRv()
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

        changeActiveMealsTv.setDebounceOnClickListener {
            viewModel.onEditActiveMealsClick()
        }

        viewModel.state.observeValue(this) { state ->
            state.dashboard?.let { dashboard ->
                with(dashboard) {
                    ownWelcomeTitle.text = getString(R.string.own_welcome, cookerName)
                    ownIsOnDuty.isChecked = activityStatus
                    ownIsDeliveryAvailable.isChecked = delivery
                    ownIsPickupAvailable.isChecked = pickup
                    ownRatingSubtitle.text = requireContext().getQuantityText(
                        R.plurals.meals_reviews,
                        reviews.totalReviews
                    )
                    ownSubscriptionDate.setTextOrHide(subscriptionExpirationDate)
                    if (reviews.totalReviews != 0) {
                        ownRatingPercentage.text = reviews.averageRating
                        ownRating.isVisible = true
                        ownRatingEnough.isVisible = false
                    } else {
                        ownRating.isVisible = false
                        ownRatingEnough.isVisible = true
                    }
                }
            }
            viewModel.activeMeals.observeValue(this) {
                if (it.isNotEmpty()) {
                    activeMealsAdapter.setMealsList(it)
                } else {
                    ownActiveMeals.isVisible = false
                }
            }

            viewModel.buttonClicked.observeValue(this) {
                when (it) {
                    BUTTON_HELP_STATUS -> showDialogWindow(
                        getString(R.string.own_status),
                        getString(R.string.dialog_help_status)
                    )
                    BUTTON_HELP_DELIVERY -> showDialogWindow(
                        getString(R.string.own_delivery_title),
                        getString(R.string.dialog_help_delivery)
                    )
                    BUTTON_BUY_CONTAINERS -> requireContext().openLink(getString(R.string.own_buy_containers_url))
                }
            }

            helpStatusTv.setDebounceOnClickListener {
                viewModel.onButtonClicked(BUTTON_HELP_STATUS)
            }

            helpDeliveryTv.setDebounceOnClickListener {
                viewModel.onButtonClicked(BUTTON_HELP_DELIVERY)
            }

            buyContainers.setDebounceOnClickListener {
                viewModel.onButtonClicked(BUTTON_BUY_CONTAINERS)
            }
        }
    }

    private fun initMealsRv() {
        activeMealsAdapter = OrdersMealsAdapter(listOf(), true) {}
        ownActiveMealsRv.adapter = activeMealsAdapter
        ownActiveMealsRv.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
    }
}