package com.gkgio.borsch_cooker.orders.offer.holder

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.gkgio.borsch_cooker.R
import com.gkgio.borsch_cooker.ext.withCenterCropRoundedCorners
import com.gkgio.borsch_cooker.orders.offer.model.MealItemModel
import com.gkgio.borsch_cooker.utils.getImageUrl
import com.gkgio.borsch_cooker.view.SyntheticViewHolder
import kotlinx.android.synthetic.main.layout_offer_meal_images_view_holder.view.*

class MealsHolderAdapter : RecyclerView.Adapter<SyntheticViewHolder>() {

    private var meals = listOf<MealItemModel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SyntheticViewHolder =
        SyntheticViewHolder.inflateFrom(
            parent,
            R.layout.layout_offer_meal_images_view_holder,
            false
        )

    override fun onBindViewHolder(holder: SyntheticViewHolder, position: Int) =
        with(holder.itemView) {
            Glide.with(offerMealImagesIv)
                .load(getImageUrl(meals[position].image))
                .placeholder(R.drawable.ic_image_placeholder)
                .withCenterCropRoundedCorners(
                    context,
                    context.resources.getInteger(R.integer.corner_radius_small_image)
                )
                .into(offerMealImagesIv)
            return@with
        }

    override fun getItemCount(): Int = meals.size

    fun setImagesList(meals: List<MealItemModel>) {
        this.meals = meals
        notifyDataSetChanged()
    }

}