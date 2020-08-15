package com.gkgio.borsch_cooker.meals

import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.gkgio.borsch_cooker.R
import com.gkgio.borsch_cooker.ext.setDebounceOnClickListener
import com.gkgio.borsch_cooker.ext.withCenterCropRoundedCorners
import com.gkgio.borsch_cooker.view.SyntheticViewHolder
import kotlinx.android.synthetic.main.layout_meals_view_holder.view.*

class MealsAdapter(
    private val isLunches: Boolean,
    val itemClick: (position: Int) -> Unit,
    val lunchClick: (position: Int) -> Unit
) : RecyclerView.Adapter<SyntheticViewHolder>() {

    private var mealsList = listOf<MealsItemUi>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SyntheticViewHolder {
        return SyntheticViewHolder.inflateFrom(parent, R.layout.layout_meals_view_holder)
    }

    override fun getItemCount(): Int = mealsList.size

    override fun onBindViewHolder(holder: SyntheticViewHolder, position: Int) =
        with(holder.itemView) {
            mealsEdit.setDebounceOnClickListener {
                itemClick(mealsList[position].id)
            }
            mealsLunch.setDebounceOnClickListener {
                lunchClick(mealsList[position].id)
            }
            mealsLunch.isVisible = isLunches
            Glide.with(mealsImage)
                .load(mealsList[position].image)
                .placeholder(R.drawable.ic_image_placeholder)
                .withCenterCropRoundedCorners(context, 8)
                .into(mealsImage)
            mealsItemName.text = mealsList[position].name
            mealsPrice.text = context.getString(R.string.orders_sum, mealsList[position].price)
        }

    fun setMealsList(mealsList: List<MealsItemUi>) {
        this.mealsList = mealsList
        notifyDataSetChanged()
    }

}