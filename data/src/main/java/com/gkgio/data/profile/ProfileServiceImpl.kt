package com.gkgio.data.profile


import com.gkgio.data.auth.UserResponse
import com.gkgio.data.auth.UserResponseTransformer
import com.gkgio.data.base.BaseService
import com.gkgio.data.exception.ServerExceptionTransformer
import com.gkgio.domain.auth.Cooker
import com.gkgio.domain.profile.ProfileService
import io.reactivex.Single
import retrofit2.http.GET
import javax.inject.Inject

class ProfileServiceImpl @Inject constructor(
    private val api: ProfileApi,
    private val userResponseTransformer: UserResponseTransformer,
    serverExceptionTransformer: ServerExceptionTransformer
) : BaseService(serverExceptionTransformer), ProfileService {

    override fun loadProfile(): Single<Cooker> =
        executeRequest(
            api.loadProfile().map { userResponseTransformer.transform(it) }
        )


    interface ProfileApi {
        @GET("cooker/profile/view/")
        fun loadProfile(): Single<UserResponse>
    }
}