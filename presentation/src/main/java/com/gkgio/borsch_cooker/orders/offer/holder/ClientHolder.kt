package com.gkgio.borsch_cooker.orders.offer.holder

import android.view.View
import androidx.core.view.isVisible
import com.gkgio.borsch_cooker.orders.offer.model.ClientModel
import com.gkgio.borsch_cooker.view.AbstractHolder
import kotlinx.android.synthetic.main.layout_offer_client_holder.view.*


open class ClientHolder(itemView: View) : AbstractHolder<ClientModel>(itemView), View.OnClickListener {

    companion object {
        const val STATUS_NEGATIVE = 0
        const val STATUS_NORMALLY = 1
    }

    override fun onClick(v: View?) {
        TODO("Not yet implemented")
    }

    override fun bind(item: ClientModel) {
        with(itemView) {
            if (item.status == STATUS_NEGATIVE) {
                iconNegativeIv.isVisible = true
                clientNegativeNameTv.text = item.name
                warningCommentTv.isVisible = true
                iconNormallyTv.isVisible = false
                clientNoNegativeTv.isVisible = false
            } else {
                iconNegativeIv.isVisible = false
                warningCommentTv.isVisible = false
                iconNormallyTv.isVisible = true
                clientNoNegativeTv.isVisible = true
            }
        }
    }
}