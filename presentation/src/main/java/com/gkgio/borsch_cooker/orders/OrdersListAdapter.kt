package com.gkgio.borsch_cooker.orders

import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.gkgio.borsch_cooker.R
import com.gkgio.borsch_cooker.ext.setDebounceOnClickListener
import com.gkgio.borsch_cooker.utils.getOrdersStatusNameByOrdersStatus
import com.gkgio.borsch_cooker.view.SyntheticViewHolder
import kotlinx.android.synthetic.main.layout_orders_view_holder.view.*

class OrdersListAdapter(
    val itemClick: (position: Int) -> Unit
) : RecyclerView.Adapter<SyntheticViewHolder>() {

    private var ordersList = listOf<OrdersListItemUi>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SyntheticViewHolder {
        val holder =
            SyntheticViewHolder.inflateFrom(parent, R.layout.layout_orders_view_holder)

        holder.itemView.setDebounceOnClickListener {
            itemClick(holder.adapterPosition)
        }

        return holder
    }

    override fun getItemCount(): Int {
        return ordersList.size
    }

    override fun onBindViewHolder(holder: SyntheticViewHolder, position: Int) =
        with(holder.itemView) {
            ordersId.text =
                context.getString(R.string.orders_number, ordersList[position].id.toString())
            ordersStatus.text =
                getOrdersStatusNameByOrdersStatus(context, ordersList[position].status)
            ordersSum.text = context.getString(R.string.orders_sum, 400) //TODO
            ordersCreatedTime.text = ordersList[position].address.createdAt //TODO
            ordersDeliveryType.text = "Самовывоз" //TODO

            val ordersMealAdapter = OrdersMealsAdapter(ordersList[position].meals) {
                println("CLICKED AT $it")
            }
            ordersMealsRv.adapter = ordersMealAdapter
            ordersMealsRv.layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        }

    fun setOrdersList(ordersList: List<OrdersListItemUi>) {
        this.ordersList = ordersList
        notifyDataSetChanged()
    }
}
