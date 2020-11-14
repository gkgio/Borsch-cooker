package com.gkgio.data.auth

import com.gkgio.data.BaseTransformer
import com.gkgio.domain.auth.Cooker
import javax.inject.Inject

class UserToResponseTransformer @Inject constructor(
    private val addressToResponseTransformer: AddressToResponseTransformer
) :
    BaseTransformer<Cooker, UserResponse> {

    override fun transform(data: Cooker) = with(data) {
        UserResponse(
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
            address?.let { addressToResponseTransformer.transform(it) }
        )
    }
}