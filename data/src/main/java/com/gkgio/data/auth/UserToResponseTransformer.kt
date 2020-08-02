package com.gkgio.data.auth

import com.gkgio.data.BaseTransformer
import com.gkgio.domain.auth.Cooker
import javax.inject.Inject

class UserToResponseTransformer @Inject constructor() :
    BaseTransformer<Cooker, UserResponse> {

    override fun transform(data: Cooker) = with(data) {
        UserResponse(
            id,
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