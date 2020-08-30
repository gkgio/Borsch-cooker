package com.gkgio.data.profile


import com.gkgio.data.base.BaseService
import com.gkgio.data.exception.ServerExceptionTransformer
import com.gkgio.domain.profile.ProfileService
import io.reactivex.Completable
import retrofit2.http.GET
import javax.inject.Inject

class ProfileServiceImpl @Inject constructor(
    private val api: ProfileApi,
    serverExceptionTransformer: ServerExceptionTransformer
) : BaseService(serverExceptionTransformer), ProfileService {

    override fun loadProfile(): Completable =
        executeRequest(
            api.loadProfile()
        )


    interface ProfileApi {
        @GET("cooker/profile/view/")
        fun loadProfile(): Completable
    }
}