package com.gkgio.data.auth

import com.gkgio.data.base.BaseService
import com.gkgio.data.exception.ServerExceptionTransformer
import com.gkgio.domain.auth.AuthService
import com.gkgio.domain.auth.ValidateSmsCode
import io.reactivex.Completable
import io.reactivex.Single
import retrofit2.http.*
import javax.inject.Inject

class AuthServiceImpl @Inject constructor(
    private val authServiceApi: AuthServiceApi,
    private val validateSmsCodeResponseTransformer: ValidateSmsCodeResponseTransformer,
    serverExceptionTransformer: ServerExceptionTransformer
) : BaseService(serverExceptionTransformer), AuthService {

    override fun getSmsCodeByPhone(inputPhone: String): Single<String> = executeRequest(
        authServiceApi.getSmsCodeByPhone(inputPhone).map { it.tmpToken }
    )

    override fun validateSmsCode(tmpToken: String, code: String): Single<ValidateSmsCode> =
        executeRequest(
            authServiceApi.validateSmsCode(
                tmpToken,
                code
            )
        ).map { validateSmsCodeResponseTransformer.transform(it) }

    override fun sendPushToken(pushToken: String?): Completable =
        if (pushToken.isNullOrEmpty()) {
            Completable.complete()
        } else {
            authServiceApi.sendPushTokenToServer(PushTokenRequest(pushToken))
        }


    interface AuthServiceApi {
        @POST("auth")
        fun getSmsCodeByPhone(@Query("phone") phone: String): Single<SmsResponse>

        @PATCH("auth/{tmpToken}")
        fun validateSmsCode(
            @Path("tmpToken") tmpToken: String,
            @Query("code") code: String
        ): Single<ValidateSmsCodeResponse>

        @POST("misc/device_tokens/gcm")
        fun sendPushTokenToServer(@Body pushTokenRequest: PushTokenRequest): Completable
    }
}