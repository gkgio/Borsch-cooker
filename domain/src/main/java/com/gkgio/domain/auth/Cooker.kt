package com.gkgio.domain.auth

import com.gkgio.domain.address.Address


data class Cooker(
    val email: String?,
    val banned: Boolean? = false,
    val certificationPending: Boolean? = false,
    val certified: Boolean? = false,
    val commission: String?,
    val delivery: Boolean? = false,
    val description: String?,
    val firstName: String?,
    val lastName: String?,
    val phone: String?,
    val avatarUrl: String?,
    val address: Address?
)