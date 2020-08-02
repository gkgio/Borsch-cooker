package com.gkgio.data.auth

import android.content.SharedPreferences
import com.gkgio.domain.auth.AuthRepository
import com.gkgio.domain.auth.Cooker
import com.squareup.moshi.Moshi
import io.reactivex.Completable
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val prefs: SharedPreferences,
    private val moshi: Moshi,
    private val userResponseToTransformer: UserToResponseTransformer,
    private val userResponseTransformer: UserResponseTransformer
) : AuthRepository {

    private companion object {
        private const val AUTH_TOKEN = "auth_token"
        private const val USER_PROFILE = "user_profile"
        private const val PUSH_TOKEN = "push_token"
    }

    override fun getAuthToken(): String? =
        prefs.getString(AUTH_TOKEN, null)

    override fun saveAuthToken(token: String) {
        prefs.edit().putString(AUTH_TOKEN, token).apply()
    }

    override fun saveUserProfile(user: Cooker) {
        prefs.edit().putString(
            USER_PROFILE,
            moshi.adapter(UserResponse::class.java)
                .toJson(
                    userResponseToTransformer.transform(user)
                )
        ).apply()
    }


    override fun loadUserProfile(): Cooker? {
        val basketCountAndSumJsonString = prefs.getString(USER_PROFILE, null)
        basketCountAndSumJsonString?.let { basketCountAndSum ->
            val data = moshi.adapter(UserResponse::class.java)
                .fromJson(basketCountAndSum)
            data?.let {
                return userResponseTransformer.transform(it)
            }
        }
        return null
    }

    override fun removeAccountData(): Completable = Completable.fromCallable {
        prefs.edit().remove(AUTH_TOKEN).apply()
        prefs.edit().remove(USER_PROFILE).apply()
    }

    override fun savePushToken(pushToken: String): Completable = Completable.fromCallable {
        prefs.edit().putString(PUSH_TOKEN, pushToken).apply()
    }

    override fun getPushToken(): String? =
        prefs.getString(PUSH_TOKEN, null)

}