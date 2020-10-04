package com.gkgio.data.auth

import com.gkgio.data.BaseTransformer
import com.gkgio.domain.auth.Cooker
import javax.inject.Inject

class UserResponseTransformer @Inject constructor() : BaseTransformer<UserResponse, Cooker> {

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
            avatarUrl
        )
    }
}