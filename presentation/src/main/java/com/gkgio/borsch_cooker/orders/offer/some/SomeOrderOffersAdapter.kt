package com.gkgio.borsch_cooker.orders.offer.some

import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.gkgio.borsch_cooker.R
import com.gkgio.borsch_cooker.ext.setDebounceOnClickListener
import com.gkgio.borsch_cooker.orders.OrdersListItemUi
import com.gkgio.borsch_cooker.orders.OrdersMealsAdapter
import com.gkgio.borsch_cooker.view.SyntheticViewHolder
import kotlinx.android.synthetic.main.layout_offers_order_view_holder.view.*

class SomeOrderOffersAdapter(
    private val actionClick: (orderId: String, action: String) -> Unit
) : RecyclerView.Adapter<SyntheticViewHolder>() {

    private var orders = listOf<OrdersListItemUi>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SyntheticViewHolder =
        SyntheticViewHolder.inflateFrom(parent, R.layout.layout_offers_order_view_holder)

    override fun onBindViewHolder(holder: SyntheticViewHolder, position: Int) =
        with(holder.itemView) {

            val adapter = OrdersMealsAdapter(false)
            val order = orders[position]

            orderOffersAcceptButton.text =
                context.getString(R.string.order_offer_accept_button2, order.price.toString())

            orderOffersMealsRv.adapter = adapter
            orderOffersMealsRv.layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter.setMealsList(order.meals)

            orderOffersDeclineButton.setDebounceOnClickListener {
                actionClick(order.id, SomeOrderOffersSheet.BUTTON_DECLINE)
            }

            orderOffersAcceptButton.setDebounceOnClickListener {
                actionClick(order.id, SomeOrderOffersSheet.BUTTON_ACCEPT)
            }
        }

    override fun getItemCount(): Int = orders.size

    fun setOrdersList(orders: List<OrdersListItemUi>) {
        this.orders = orders
        notifyDataSetChanged()
    }

}