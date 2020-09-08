package com.gkgio.data.auth

import com.gkgio.data.base.BaseService
import com.gkgio.data.exception.ServerExceptionTransformer
import com.gkgio.domain.auth.AuthService
import com.gkgio.domain.auth.Cooker
import com.gkgio.domain.auth.ValidateSmsCode
import io.reactivex.Completable
import io.reactivex.Single
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.http.*
import java.io.File
import javax.inject.Inject


class AuthServiceImpl @Inject constructor(
    private val authServiceApi: AuthServiceApi,
    private val validateSmsCodeResponseTransformer: ValidateSmsCodeResponseTransformer,
    private val userResponseTransformer: UserResponseTransformer,
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

    override fun saveName(name: String, secondName: String): Single<Cooker> =
        authServiceApi.saveName(name, secondName)
            .map { userResponseTransformer.transform(it) }

    override fun saveAvatar(file: File): Single<Cooker> {
        val filePart = MultipartBody.Part.createFormData(
            "file",
            file.name,
            file.asRequestBody("image/*".toMediaTypeOrNull())
        )
        return authServiceApi.saveAvatar(filePart)
            .map { userResponseTransformer.transform(it) }
    }

    interface AuthServiceApi {
        @POST("cooker/auth")
        fun getSmsCodeByPhone(@Query("phone") phone: String): Single<SmsResponse>

        @PATCH("cooker/auth/{tmpToken}")
        fun validateSmsCode(
            @Path("tmpToken") tmpToken: String,
            @Query("code") code: String
        ): Single<ValidateSmsCodeResponse>

        @POST("cooker/misc/device_tokens/gcm")
        fun sendPushTokenToServer(@Body pushTokenRequest: PushTokenRequest): Completable

        @POST("cooker/add_name")
        fun saveName(
            @Query("first_name") name: String,
            @Query("last_name") secondName: String
        ): Single<UserResponse>

        @Multipart
        @POST("cooker/avatar")
        fun saveAvatar(@Part filePart: MultipartBody.Part): Single<UserResponse>
    }
}