package com.gkgio.domain.auth

import io.reactivex.Completable

interface AuthRepository {
    fun getAuthToken(): String?
    fun saveAuthToken(token: String)
    fun saveUserProfile(user: User)
    fun loadUserProfile(): User?
    fun removeAccountData(): Completable
    fun savePushToken(pushToken: String): Completable
    fun getPushToken(): String?
}