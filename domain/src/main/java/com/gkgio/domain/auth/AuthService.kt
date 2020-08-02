package com.gkgio.domain.auth

import io.reactivex.Completable
import io.reactivex.Single

interface AuthService {
    fun getSmsCodeByPhone(inputPhone: String): Single<Long>
    fun validateSmsCode(tmpToken: Long, code: String): Single<ValidateSmsCode>
    fun sendPushToken(pushToken: String?): Completable
}