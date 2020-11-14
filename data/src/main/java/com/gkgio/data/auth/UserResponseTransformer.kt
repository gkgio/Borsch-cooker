package com.gkgio.data.auth

import com.gkgio.data.BaseTransformer
import com.gkgio.data.address.adding.AddressResponseTransformer
import com.gkgio.domain.auth.Cooker
import javax.inject.Inject

class UserResponseTransformer @Inject constructor(
    private val addressResponseTransformer: AddressResponseTransformer
) : BaseTransformer<UserResponse, Cooker> {

    override fun transform(data: UserResponse) = with(data) {
        Cooker(
            email,
            banned,
            certificationPending,
            certified,
            commission,
            delivery,
            description,
            firstName,
            lastName,
            phone,
            avatarUrl,
            address?.let { addressResponseTransformer.transform(it) }
        )
    }
}