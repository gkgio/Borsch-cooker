package com.gkgio.domain.auth


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
    val avatarUrl: String?
)