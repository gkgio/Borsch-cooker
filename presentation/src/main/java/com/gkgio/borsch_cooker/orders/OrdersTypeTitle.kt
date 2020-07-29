package com.gkgio.borsch_cooker.orders

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class OrdersTypeTitle(
    var title: String,
    var isSelected: Boolean = false
) : Parcelable