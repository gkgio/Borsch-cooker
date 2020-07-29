package com.gkgio.borsch_cooker.orders

import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.gkgio.borsch_cooker.R
import com.gkgio.borsch_cooker.ext.getColorCompat
import com.gkgio.borsch_cooker.ext.setDebounceOnClickListener
import com.gkgio.borsch_cooker.utils.getOrdersTypeNameByOrdersType
import com.gkgio.borsch_cooker.view.SyntheticViewHolder
import kotlinx.android.synthetic.main.orders_title_view_holder.*

class OrdersTypeTitlesAdapter(
    val itemClick: (position: Int) -> Unit
) : RecyclerView.Adapter<SyntheticViewHolder>() {

    private val typeTitles = mutableListOf<OrdersTypeTitle>()

    private var selectedPosition: Int = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SyntheticViewHolder {
        val holder =
            SyntheticViewHolder.inflateFrom(parent, R.layout.orders_title_view_holder)

        holder.itemView.setDebounceOnClickListener {
            itemClick(holder.adapterPosition)
        }

        return holder
    }

    override fun onBindViewHolder(holder: SyntheticViewHolder, position: Int) =
        with(holder) {
            val typeTitle = typeTitles[position]

            typeName.text = getOrdersTypeNameByOrdersType(itemView.context, typeTitle.title)
            typeName.setTextColor(
                if (typeTitle.isSelected) {
                    itemView.context.getColorCompat(R.color.blue)
                } else {
                    itemView.context.getColorCompat(R.color.black)
                }
            )

            tabSelectedIndicator.isVisible = typeTitle.isSelected
        }

    override fun getItemCount(): Int {
        return typeTitles.size
    }

    fun setTitlesRes(dateTitles: List<OrdersTypeTitle>) {
        this.typeTitles.clear()
        this.typeTitles.addAll(dateTitles)
        notifyDataSetChanged()
    }

    fun markAsSelected(position: Int) {
        if (itemCount > position && position != selectedPosition) {

            typeTitles[selectedPosition].isSelected = false
            typeTitles[position].isSelected = true

            val oldSelectedPosition = selectedPosition
            selectedPosition = position
            notifyItemChanged(position)
            notifyItemChanged(oldSelectedPosition)
        }
    }
}