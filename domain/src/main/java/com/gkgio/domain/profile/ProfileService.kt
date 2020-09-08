package com.gkgio.domain.profile

import io.reactivex.Completable

interface ProfileService {
    fun loadProfile(): Completable
}