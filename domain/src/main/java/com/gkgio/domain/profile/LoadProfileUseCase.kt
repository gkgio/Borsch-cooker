package com.gkgio.domain.profile

import com.gkgio.domain.auth.Cooker
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject

interface LoadProfileUseCase {
    fun loadProfile(): Single<Cooker>
}

class LoadProfileUseCaseImpl @Inject constructor(
    private val profileService: ProfileService
) : LoadProfileUseCase {

    override fun loadProfile(): Single<Cooker> =
        profileService.loadProfile()
}