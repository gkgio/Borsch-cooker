package com.gkgio.data.own

import com.gkgio.data.base.BaseService
import com.gkgio.data.exception.ServerExceptionTransformer
import com.gkgio.domain.own.OwnService
import io.reactivex.Single
import retrofit2.http.*
import javax.inject.Inject

class OwnServiceImpl @Inject constructor(
    private val ownApi: OwnApi,
    serverExceptionTransformer: ServerExceptionTransformer
) : BaseService(serverExceptionTransformer), OwnService {

    override fun setDutyStatus(isOnDuty: Boolean) : Single<String> =
        executeRequest(
            ownApi.setDutyStatus(isOnDuty).map { it }
        )

    override fun setDeliveryStatus(isDeliveryAvailable: Boolean) : Single<String> =
        executeRequest(
            ownApi.setDeliveryStatus(isDeliveryAvailable).map { it }
        )

    interface OwnApi {
        @POST("on_duty")
        fun setDutyStatus(
            @Query("on_duty") isOnDuty: Boolean
        ) : Single<String>

        @POST("delivery")
        fun setDeliveryStatus(
            @Query("delivery") isDeliveryAvailable: Boolean
        ) : Single<String>
    }
}