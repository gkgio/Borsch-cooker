package com.gkgio.borsch_cooker.meals.addmeal.edit

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gkgio.borsch_cooker.R
import com.gkgio.borsch_cooker.ext.setDebounceOnClickListener
import com.gkgio.borsch_cooker.view.SyntheticViewHolder
import kotlinx.android.synthetic.main.linear_add_lunch_sale_view_holder.view.*

class AddMealSaleAdapter(private val itemClick: (saleSize: Int) -> Unit) :
    RecyclerView.Adapter<SyntheticViewHolder>() {

    private var checkedSize: Int? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SyntheticViewHolder {
        return SyntheticViewHolder.inflateFrom(parent, R.layout.linear_add_lunch_sale_view_holder)
    }

    override fun onBindViewHolder(holder: SyntheticViewHolder, position: Int) =
        with(holder.itemView) {
            val saleSize = ((position + 1) * 5)
            saleSizeTv.text = "$saleSize%"
            saleSizeTv.setDebounceOnClickListener {
                checkedSize = position
                notifyDataSetChanged()
                itemClick(saleSize)
            }
            if (position == checkedSize) {
                saleSizeTv.background = resources.getDrawable(R.drawable.radio_button_checked)
                saleSizeTv.setTextColor(resources.getColor(R.color.white))
            } else {
                saleSizeTv.background = resources.getDrawable(R.drawable.radio_button_unchecked)
                saleSizeTv.setTextColor(resources.getColor(R.color.black))
            }
        }

    override fun getItemCount(): Int = 6

}