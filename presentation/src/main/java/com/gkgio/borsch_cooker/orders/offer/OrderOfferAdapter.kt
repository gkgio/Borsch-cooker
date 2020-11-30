package com.gkgio.borsch_cooker.orders.offer

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.gkgio.borsch_cooker.R
import com.gkgio.borsch_cooker.ext.setDebounceOnClickListener
import com.gkgio.borsch_cooker.orders.offer.holder.ClientHolder
import com.gkgio.borsch_cooker.orders.offer.holder.DeliveryHolder
import com.gkgio.borsch_cooker.orders.offer.holder.MealsHolder
import com.gkgio.borsch_cooker.orders.offer.holder.PickupHolder
import com.gkgio.borsch_cooker.orders.offer.model.ClientModel
import com.gkgio.borsch_cooker.orders.offer.model.DeliveryModel
import com.gkgio.borsch_cooker.orders.offer.model.MealModel
import com.gkgio.borsch_cooker.orders.offer.model.PickupModel
import com.gkgio.borsch_cooker.orders.offer.sheet.DeliverySheet
import com.gkgio.borsch_cooker.orders.offer.sheet.MealsSheet
import com.gkgio.borsch_cooker.view.AbstractHolder

class OrderOfferAdapter : RecyclerView.Adapter<AbstractHolder<*>>() {

    private var orderInfoList = listOf<Any>()

    companion object {
        const val TYPE_MEALS = 1
        const val TYPE_DELIVERY = 2
        const val TYPE_CLIENT_INFO = 3
        const val TYPE_PICKUP = 4
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AbstractHolder<*> =
        with(parent) {
            when (viewType) {
                TYPE_MEALS -> MealsHolder(
                    LayoutInflater.from(context)
                        .inflate(R.layout.layout_offer_meals_holder, parent, false)
                )
                TYPE_DELIVERY -> DeliveryHolder(
                    LayoutInflater.from(context)
                        .inflate(R.layout.layout_offer_delivery_holder, parent, false)
                )
                TYPE_CLIENT_INFO -> ClientHolder(
                    LayoutInflater.from(context)
                        .inflate(R.layout.layout_offer_client_holder, parent, false)
                )
                TYPE_PICKUP -> PickupHolder(
                    LayoutInflater.from(context)
                        .inflate(R.layout.layout_offer_pickup_holder, parent, false)
                )
                else -> throw IllegalArgumentException("Unsupported viewType: $viewType")
            }
        }

    override fun onBindViewHolder(holder: AbstractHolder<*>, position: Int) = with(holder) {
        bindHolder(orderInfoList[position])
        val fm = ((holder.itemView.context as AppCompatActivity).supportFragmentManager)
        itemView.setDebounceOnClickListener {
            when (itemViewType) {
                TYPE_MEALS -> MealsSheet(orderInfoList[position] as MealModel).show(fm, "")
                TYPE_DELIVERY -> DeliverySheet(orderInfoList[position] as DeliveryModel).show(
                    fm,
                    ""
                )
            }
        }
    }

    override fun getItemCount(): Int = orderInfoList.size

    override fun getItemViewType(position: Int): Int =
        when (orderInfoList[position]) {
            is MealModel -> TYPE_MEALS
            is DeliveryModel -> TYPE_DELIVERY
            is PickupModel -> TYPE_PICKUP
            is ClientModel -> TYPE_CLIENT_INFO
            else -> 0
        }

    fun setOrderInfo(orderInfoList: List<Any>) {
        this.orderInfoList = orderInfoList
        notifyItemRangeInserted(this.orderInfoList.size, orderInfoList.size)
    }

}