package com.gkgio.domain.profile

import io.reactivex.Completable
import javax.inject.Inject

interface LoadProfileUseCase {
    fun loadProfile(): Completable
}

class LoadProfileUseCaseImpl @Inject constructor(
    private val profileService: ProfileService
) : LoadProfileUseCase {

    override fun loadProfile(): Completable =
        profileService.loadProfile()
}