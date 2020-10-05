package com.gkgio.borsch_cooker.meals

import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.gkgio.borsch_cooker.R
import com.gkgio.borsch_cooker.ext.setDebounceOnClickListener
import com.gkgio.borsch_cooker.ext.withCenterCropRoundedCorners
import com.gkgio.borsch_cooker.utils.getImageUrl
import com.gkgio.borsch_cooker.view.SyntheticViewHolder
import kotlinx.android.synthetic.main.layout_meals_view_holder.view.*

class MealsAdapter(
    private val isLunches: Boolean,
    val itemClick: (position: String) -> Unit,
    val lunchClick: (position: String) -> Unit
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
            mealsLunchIv.setDebounceOnClickListener {
                lunchClick(mealsList[position].id)
            }
            mealsLunchIv.isVisible = isLunches
            Glide.with(mealsImage)
                .load(if (mealsList[position].images.isEmpty()) null else getImageUrl(mealsList[position].images.first()))
                .placeholder(R.drawable.ic_image_placeholder)
                .withCenterCropRoundedCorners(context, 8)
                .into(mealsImage)
            mealsItemName.text = mealsList[position].name
            mealsPrice.text =
                context.getString(R.string.orders_sum, mealsList[position].price.toString())
        }

    fun setMealsList(mealsList: List<MealsItemUi>?) {
        this.mealsList = if (mealsList.isNullOrEmpty()) listOf() else mealsList
        notifyDataSetChanged()
    }

}