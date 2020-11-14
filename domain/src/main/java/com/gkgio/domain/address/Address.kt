package com.gkgio.domain.address

data class Address(
    val id: String,
    val city: String?,
    val country: String?,
    val flat: String?,
    val house: String?,
    val location: Coordinates,
    val street: String?,
    val block: String?
)