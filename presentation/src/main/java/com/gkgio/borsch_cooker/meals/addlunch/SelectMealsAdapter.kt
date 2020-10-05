package com.gkgio.borsch_cooker.meals.addlunch

import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.gkgio.borsch_cooker.R
import com.gkgio.borsch_cooker.ext.setDebounceOnClickListener
import com.gkgio.borsch_cooker.ext.withCenterCropRoundedCorners
import com.gkgio.borsch_cooker.meals.MealsItemUi
import com.gkgio.borsch_cooker.utils.getImageUrl
import com.gkgio.borsch_cooker.view.SyntheticViewHolder
import kotlinx.android.synthetic.main.layout_select_meals_view_holder.view.*

class SelectMealsAdapter(
    private val itemClick: (id: String, checked: Boolean) -> Unit
) : RecyclerView.Adapter<SyntheticViewHolder>() {

    private var mealsList = listOf<MealsItemUi>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SyntheticViewHolder {
        return SyntheticViewHolder.inflateFrom(parent, R.layout.layout_select_meals_view_holder)
    }

    override fun onBindViewHolder(holder: SyntheticViewHolder, position: Int) =
        with(holder.itemView) {
            val meal = mealsList[position]
            Glide.with(selectMealsIv)
                .load(if (meal.images.isEmpty()) null else getImageUrl(meal.images.first()))
                .placeholder(R.drawable.ic_image_placeholder)
                .withCenterCropRoundedCorners(context, 8)
                .into(selectMealsIv)
            selectMealsNameTv.text = meal.name
            selectMealsPriceTv.text = context.getString(R.string.orders_sum, meal.price.toString())
            selectMealsPortionsTv.text = meal.portions.toString()
            selectMealsUnavailableTv.isVisible != meal.available
            selectMealsCb.isVisible = meal.available
            selectMealsCb.setDebounceOnClickListener {
                itemClick(meal.id, selectMealsCb.isChecked)
            }
        }

    override fun getItemCount(): Int = mealsList.size

    fun setMealsList(mealsList: List<MealsItemUi>?) {
        this.mealsList = if (mealsList.isNullOrEmpty()) listOf() else mealsList
        notifyDataSetChanged()
    }

}