package com.gkgio.domain.auth

import io.reactivex.Completable

interface AuthRepository {
    fun getAuthToken(): String?
    fun saveAuthToken(token: String)
    fun saveUserProfile(user: Cooker)
    fun loadUserProfile(): Cooker?
    fun removeAccountData(): Completable
    fun savePushToken(pushToken: String): Completable
    fun getPushToken(): String?
}