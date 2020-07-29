package com.gkgio.domain.orders

data class OrdersAddressItemData(
    val id: Int,
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
    val ownerId: Int,
    val ownerType: String,
    val createdAt: String,
    val updatedAt: String,
    val main: Boolean
)