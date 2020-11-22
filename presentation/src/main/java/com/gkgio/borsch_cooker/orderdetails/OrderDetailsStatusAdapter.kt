package com.gkgio.borsch_cooker.orderdetails

import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.gkgio.borsch_cooker.R
import com.gkgio.borsch_cooker.ext.setDebounceOnClickListener
import com.gkgio.borsch_cooker.orders.OrdersConstants
import com.gkgio.borsch_cooker.view.SyntheticViewHolder
import kotlinx.android.synthetic.main.layout_order_status_view_holder.view.*

class OrderDetailsStatusAdapter(
        currentStatus: String,
        private val itemClick: (status: String) -> Unit
) : RecyclerView.Adapter<SyntheticViewHolder>() {

    private var newStatus = currentStatus
    private val statusList = listOf(
            OrdersConstants.ORDERS_STATUS_COOKING,
            OrdersConstants.ORDERS_STATUS_CAN_PICKUP,
            OrdersConstants.ORDERS_STATUS_DELIVERING,
            OrdersConstants.ORDERS_STATUS_CANCELED
    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SyntheticViewHolder =
            SyntheticViewHolder.inflateFrom(parent, R.layout.layout_order_status_view_holder)

    override fun onBindViewHolder(holder: SyntheticViewHolder, position: Int) =
            with(holder.itemView) {
                val status = statusList[position]
                orderStatusCheckedIv.isVisible = status == newStatus
                orderStatusTv.text = when (status) {
                    OrdersConstants.ORDERS_STATUS_COOKING -> context.getString(R.string.orders_status_cooking)
                    OrdersConstants.ORDERS_STATUS_CAN_PICKUP -> context.getString(R.string.orders_status_can_pickup)
                    OrdersConstants.ORDERS_STATUS_DELIVERING -> context.getString(R.string.orders_status_delivering)
                    OrdersConstants.ORDERS_STATUS_CANCELED -> context.getString(R.string.orders_status_canceled)
                    else -> "Unknown"
                }

                setDebounceOnClickListener {
                    setNewStatus(status)
                    itemClick(status)
                }
            }

    override fun getItemCount(): Int = statusList.size

    private fun setNewStatus(newStatus: String) {
        this.newStatus = newStatus
        notifyDataSetChanged()
    }

}