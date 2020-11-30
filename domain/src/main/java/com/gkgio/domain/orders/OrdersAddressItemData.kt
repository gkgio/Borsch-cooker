package com.gkgio.domain.orders

data class OrdersAddressItemData(
        val id: String,
        val area: String?,
        val block: String?,
        val city: String,
        val cityDistrict: String?,
        val country: String,
        val flat: String?,
        val floor: String?,
        val house: String,
        val intercom: String?,
        val region: String,
        val street: String,
        val ownerId: Long,
        val ownerType: String,
        val createdAt: String,
        val updatedAt: String,
        val main: Boolean
)