package com.gkgio.domain.address

data class AddressAddingRequest(
    val city: String?,
    val country: String?,
    val flat: String?,
    val house: String?,
    val location: Coordinates,
    val street: String?,
    val block: String?
)