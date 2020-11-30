package com.gkgio.borsch_cooker.orders

import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.gkgio.borsch_cooker.R
import com.gkgio.borsch_cooker.ext.setDebounceOnClickListener
import com.gkgio.borsch_cooker.utils.dateToUIStringTimeAndDay
import com.gkgio.borsch_cooker.utils.getOrdersStatusNameByOrdersStatus
import com.gkgio.borsch_cooker.view.SyntheticViewHolder
import kotlinx.android.synthetic.main.layout_orders_view_holder.view.*

class OrdersListAdapter(
        val itemClick: (orderId: String) -> Unit
) : RecyclerView.Adapter<SyntheticViewHolder>() {

    private var ordersList = listOf<OrdersListItemUi>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SyntheticViewHolder {
        val holder =
                SyntheticViewHolder.inflateFrom(parent, R.layout.layout_orders_view_holder)

        holder.itemView.setDebounceOnClickListener {
            itemClick(ordersList[holder.adapterPosition].id)
        }

        return holder
    }

    override fun getItemCount(): Int {
        return ordersList.size
    }

    override fun onBindViewHolder(holder: SyntheticViewHolder, position: Int) =
            with(holder.itemView) {
                val order = ordersList[position]
                ordersId.text =
                        context.getString(R.string.orders_number, order.id)
                ordersStatus.text = order.statusUi
                ordersSum.text = context.getString(R.string.orders_sum, order.price.toString())
                ordersCreatedTime.text = dateToUIStringTimeAndDay(order.createdAt)
                ordersDeliveryType.text =
                        if (order.type == OrdersConstants.ORDERS_TAKE_DELIVERY)
                            context.getString(R.string.order_delivery) else context.getString(R.string.order_pickup)

                val ordersMealAdapter = OrdersMealsAdapter(false)
                ordersMealsRv.adapter = ordersMealAdapter
                ordersMealsRv.layoutManager =
                        LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                ordersMealAdapter.setMealsList(order.meals)
            }

    fun setOrdersList(ordersList: List<OrdersListItemUi>) {
        this.ordersList = ordersList
        notifyDataSetChanged()
    }
}
