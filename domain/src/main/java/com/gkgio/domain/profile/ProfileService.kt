package com.gkgio.domain.profile

import com.gkgio.domain.auth.Cooker
import io.reactivex.Single

interface ProfileService {
    fun loadProfile(): Single<Cooker>
}