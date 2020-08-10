package com.gkgio.domain.auth

import io.reactivex.Completable
import io.reactivex.Single
import java.io.File

interface AuthService {
    fun getSmsCodeByPhone(inputPhone: String): Single<String>
    fun validateSmsCode(tmpToken: String, code: String): Single<ValidateSmsCode>
    fun sendPushToken(pushToken: String?): Completable
    fun saveName(name: String, secondName: String): Single<Cooker>
    fun saveAvatar(file: File): Single<Cooker>
}