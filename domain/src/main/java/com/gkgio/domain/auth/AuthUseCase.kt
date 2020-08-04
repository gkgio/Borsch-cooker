package com.gkgio.domain.auth

import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

interface AuthUseCase {
    fun getSmsCodeByPhone(inputPhone: String): Single<String>
    fun validateSmsCode(tmpToken: String, code: String): Completable
    fun getAuthToken(): String?
    fun saveAuthToken(token: String)
    fun saveUserProfile(user: Cooker)
    fun loadUserProfile(): Cooker?
    fun savePushToken(pushToken: String): Completable
    fun sendPushToken(pushToken: String): Completable
}

class AuthUseCaseImpl @Inject constructor(
    private val authService: AuthService,
    private val authRepository: AuthRepository
) : AuthUseCase {

    override fun getSmsCodeByPhone(inputPhone: String): Single<String> =
        authService.getSmsCodeByPhone(inputPhone)

    override fun validateSmsCode(tmpToken: String, code: String): Completable =
        authService.validateSmsCode(tmpToken, code)
            .flatMapCompletable { validateSmsCode ->
                saveAuthToken(validateSmsCode.token)
                saveUserProfile(validateSmsCode.cooker)
                authService.sendPushToken(authRepository.getPushToken())
            }


    override fun getAuthToken(): String? =
        authRepository.getAuthToken()

    override fun saveAuthToken(token: String) =
        authRepository.saveAuthToken(token)

    override fun saveUserProfile(user: Cooker) =
        authRepository.saveUserProfile(user)

    override fun loadUserProfile(): Cooker? =
        authRepository.loadUserProfile()

    override fun savePushToken(pushToken: String): Completable =
        authRepository.savePushToken(pushToken)
            .doOnComplete {
                if (authRepository.getAuthToken() != null) {
                    authService.sendPushToken(pushToken)
                        .subscribeOn(Schedulers.io())
                }
            }

    override fun sendPushToken(pushToken: String): Completable =
        authService.sendPushToken(pushToken)
}