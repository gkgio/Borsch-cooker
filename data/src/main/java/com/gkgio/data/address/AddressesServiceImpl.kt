package com.gkgio.data.address

import com.gkgio.data.address.adding.AddressAddingDataRequest
import com.gkgio.data.address.adding.AddressAddingDataRequestTransformer
import com.gkgio.data.address.suggestions.GeoSuggestionsDataRequest
import com.gkgio.data.address.suggestions.GeoSuggestionsDataRequestTransformer
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
    private val geoSuggestionsDataRequestTransformer: GeoSuggestionsDataRequestTransformer,
    private val geoSuggestionsListResponseTransformer: GeoSuggestionsListResponseTransformer,
    private val addressAddingDataRequestTransformer: AddressAddingDataRequestTransformer,
    serverExceptionTransformer: ServerExceptionTransformer
) : BaseService(serverExceptionTransformer), AddressesService {

    override fun loadGeoSuggestions(geoSuggestionsRequest: GeoSuggestionsRequest): Single<GeoSuggestionsList> =
        executeRequest(
            addressServiceApi.getGeoSuggestions(
                geoSuggestionsDataRequestTransformer.transform(
                    geoSuggestionsRequest
                )
            ).map { geoSuggestionsListResponseTransformer.transform(it) }
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
        fun getGeoSuggestions(@Body geoSuggestionsDataRequest: GeoSuggestionsDataRequest): Single<GeoSuggestionsListResponse>

        @POST("cooker/addresses/add")
        fun addSelectedAddress(@Body addressAddingDataRequest: AddressAddingDataRequest): Completable
    }

}