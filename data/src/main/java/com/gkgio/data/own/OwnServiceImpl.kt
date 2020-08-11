package com.gkgio.data.own

import com.gkgio.data.base.BaseService
import com.gkgio.data.exception.ServerExceptionTransformer
import com.gkgio.domain.own.OwnDashboardData
import com.gkgio.domain.own.OwnService
import io.reactivex.Single
import retrofit2.http.*
import javax.inject.Inject

class OwnServiceImpl @Inject constructor(
    private val ownApi: OwnApi,
    private val ownDashboardDataResponseTransformer: OwnDashboardDataResponseTransformer,
    serverExceptionTransformer: ServerExceptionTransformer
) : BaseService(serverExceptionTransformer), OwnService {

    override fun setDutyStatus(isOnDuty: Boolean): Single<OwnDashboardData> =
        executeRequest(
            ownApi.setDutyStatus(isOnDuty).map
            { ownDashboardDataResponseTransformer.transform(it) }
        )

    override fun setDeliveryStatus(isDeliveryAvailable: Boolean): Single<OwnDashboardData> =
        executeRequest(
            ownApi.setDeliveryStatus(isDeliveryAvailable).map
            { ownDashboardDataResponseTransformer.transform(it) }
        )

    override fun setPickupStatus(isPickupAvailable: Boolean): Single<OwnDashboardData> =
        executeRequest(
            ownApi.setPickupStatus(isPickupAvailable).map
            { ownDashboardDataResponseTransformer.transform(it) }
        )

    override fun loadDashboardData(): Single<OwnDashboardData> =
        executeRequest(
            ownApi.loadDashboardData().map {
                ownDashboardDataResponseTransformer.transform(it)
            }
        )

    interface OwnApi {
        @POST("on_duty")
        fun setDutyStatus(
            @Query("on_duty") isOnDuty: Boolean
        ): Single<OwnDashboardDataResponse>

        @POST("delivery")
        fun setDeliveryStatus(
            @Query("delivery") isDeliveryAvailable: Boolean
        ): Single<OwnDashboardDataResponse>

        @POST("pickup")
        fun setPickupStatus(
            @Query("pickup") isPickupAvailable: Boolean
        ): Single<OwnDashboardDataResponse>

        @GET("dashboard/main")
        fun loadDashboardData(): Single<OwnDashboardDataResponse>
    }
}