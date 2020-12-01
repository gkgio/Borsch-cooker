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
import com.gkgio.borsch_cooker.orders.offer.some.SomeOrderOffersSheet
import com.gkgio.borsch_cooker.subscription.SubscriptionSheet
import com.gkgio.borsch_cooker.utils.DialogUtils
import com.gkgio.borsch_cooker.utils.dateToUIStringDayAndMonth
import kotlinx.android.synthetic.main.fragment_own.*

class OwnFragment : BaseFragment<OwnViewModel>() {

    private lateinit var activeMealsAdapter: OrdersMealsAdapter

    companion object {
        val TAG = OwnFragment::class.java.simpleName
        const val BUTTON_HELP_STATUS = "helpStatus"
        const val BUTTON_HELP_DELIVERY = "helpDelivery"
    }

    override fun getLayoutId(): Int = R.layout.fragment_own

    override fun provideViewModel() = createViewModel {
        AppInjector.appComponent.ownViewModel
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initMealsRv()
        showDialog(SubscriptionSheet(), TAG)
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

                    ownSubscriptionDate.setTextOrHide(getString(R.string.own_subscription_for, dateToUIStringDayAndMonth(subscriptionExpirationDate)))

                    if (reviews.totalReviews != 0) {
                        ownRatingPercentage.text = reviews.averageRating
                        ownRating.isVisible = true
                        ownRatingEnough.isVisible = false
                    } else {
                        ownRating.isVisible = false
                        ownRatingEnough.isVisible = true
                    }
                }

                if (dashboard.activityStatus)
                    viewModel.onStartCatchOrders() else viewModel.onStopCatchOrders()
            }

            viewModel.activeMeals.observeValue(this) {
                activeMealsAdapter.setMealsList(it)
            }

            viewModel.singleEvent.observeValue(this) {
                when (it) {
                    is SingleOwnViewModelEvent.HelpStatus -> DialogUtils.showDialog(
                            TAG,
                            requireActivity().supportFragmentManager,
                            getString(R.string.dialog_help_status),
                            getString(R.string.dialog_success_button),
                            title = getString(R.string.own_status))
                    is SingleOwnViewModelEvent.HelpDelivery -> DialogUtils.showDialog(
                            TAG,
                            requireActivity().supportFragmentManager,
                            getString(R.string.dialog_help_delivery),
                            getString(R.string.dialog_success_button),
                            title = getString(R.string.own_delivery_title))
                    is SingleOwnViewModelEvent.BuyContainers -> requireContext().openLink(getString(R.string.own_buy_containers_url))
                }
            }

            viewModel.someOrderOffers.observeValue(this) {
                showDialog(SomeOrderOffersSheet.newInstance(it), TAG)
            }

            helpStatusTv.setDebounceOnClickListener {
                viewModel.onHelpClicked(BUTTON_HELP_STATUS)
            }

            helpDeliveryTv.setDebounceOnClickListener {
                viewModel.onHelpClicked(BUTTON_HELP_DELIVERY)
            }

            buyContainers.setDebounceOnClickListener {
                viewModel.onBuyContainersClicked()
            }
        }
    }

    override fun onPause() {
        super.onPause()
        viewModel.onStopCatchOrders()
    }

    override fun onResume() {
        super.onResume()
        viewModel.onStartCatchOrders()
    }

    private fun initMealsRv() {
        activeMealsAdapter = OrdersMealsAdapter(true)
        ownActiveMealsRv.adapter = activeMealsAdapter
        ownActiveMealsRv.layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
    }
}