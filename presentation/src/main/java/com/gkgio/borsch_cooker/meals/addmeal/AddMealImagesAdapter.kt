package com.gkgio.borsch_cooker.meals.addmeal

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.gkgio.borsch_cooker.R
import com.gkgio.borsch_cooker.ext.setDebounceOnClickListener
import com.gkgio.borsch_cooker.ext.withCenterCropRoundedCorners
import com.gkgio.borsch_cooker.view.SyntheticViewHolder
import kotlinx.android.synthetic.main.layout_add_meal_image_view_holder.view.*
import java.io.File

class AddMealImagesAdapter(
    val itemClick: (position: Int) -> Unit
) :
    RecyclerView.Adapter<SyntheticViewHolder>() {

    private var imagesList = listOf<File?>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SyntheticViewHolder {
        return SyntheticViewHolder.inflateFrom(parent, R.layout.layout_add_meal_image_view_holder)
    }

    override fun getItemCount(): Int = imagesList.size

    override fun onBindViewHolder(holder: SyntheticViewHolder, position: Int) =
        with(holder.itemView) {
            Glide.with(addMealImageView)
                .load(imagesList[position])
                .placeholder(R.drawable.ic_image_placeholder)
                .withCenterCropRoundedCorners(
                    context,
                    context.resources.getInteger(R.integer.corner_radius_small_image)
                )
                .into(addMealImageView)
            addMealDeleteButton.setDebounceOnClickListener {
                itemClick(position)
            }
        }

    fun setImagesList(images: List<File?>) {
        this.imagesList = images
        notifyDataSetChanged()
    }

}