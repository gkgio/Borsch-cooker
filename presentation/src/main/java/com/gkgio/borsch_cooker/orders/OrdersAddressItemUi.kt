package com.gkgio.borsch_cooker.orders

import java.io.Serializable

data class OrdersAddressItemUi(
    val id: String,
    val area: String,
    val block: String,
    val city: String,
    val cityDistrict: String,
    val country: String,
    val flat: String,
    val floor: String,
    val house: String,
    val intercom: String,
    val region: String,
    val street: String,
    val ownerId: Long,
    val ownerType: String,
    val createdAt: String,
    val updatedAt: String,
    val main: Boolean
) : Serializable