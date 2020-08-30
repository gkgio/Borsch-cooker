package com.gkgio.data.address

import com.gkgio.data.address.adding.AddressAddingDataRequest
import com.gkgio.data.address.adding.AddressAddingDataRequestTransformer
import com.gkgio.data.address.suggestions.GeoSuggestionsListResponse
import com.gkgio.data.address.suggestions.GeoSuggestionsListResponseTransformer
import com.gkgio.data.base.BaseService
import com.gkgio.data.exception.ServerExceptionTransformer
import com.gkgio.domain.address.*
import io.reactivex.Completable
import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Query
import javax.inject.Inject

class AddressesServiceImpl @Inject constructor(
    private val addressServiceApi: AddressServiceApi,
    private val geoSuggestionsListResponseTransformer: GeoSuggestionsListResponseTransformer,
    private val addressAddingDataRequestTransformer: AddressAddingDataRequestTransformer,
    serverExceptionTransformer: ServerExceptionTransformer
) : BaseService(serverExceptionTransformer), AddressesService {

    override fun loadGeoSuggestions(query: String): Single<GeoSuggestionsList> =
        executeRequest(
            addressServiceApi.getGeoSuggestions(query)
                .map { geoSuggestionsListResponseTransformer.transform(it) }
        )

    override fun addSelectedAddress(addressAddingRequest: AddressAddingRequest): Completable =
        executeRequest(
            addressServiceApi.addSelectedAddress(
                addressAddingDataRequestTransformer.transform(
                    addressAddingRequest
                )
            )
        )

    interface AddressServiceApi {
        @POST("geo/suggestions")
        fun getGeoSuggestions(
            @Query("query") query: String
        ): Single<GeoSuggestionsListResponse>

        @POST("geo/get_address")
        fun addSelectedAddress(@Body addressAddingDataRequest: AddressAddingDataRequest): Completable
    }

}