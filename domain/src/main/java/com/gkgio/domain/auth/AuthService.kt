package com.gkgio.domain.auth

import io.reactivex.Completable
import io.reactivex.Single

interface AuthService {
    fun getSmsCodeByPhone(inputPhone: String): Single<String>
    fun validateSmsCode(tmpToken: String, code: String): Single<ValidateSmsCode>
    fun sendPushToken(pushToken: String?): Completable
}