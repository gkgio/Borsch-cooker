package com.gkgio.domain.address

import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject

interface LoadAddressesUseCase {
    fun loadGeoSuggestions(query: String): Single<GeoSuggestionsList>
    fun addNewClientAddress(addressesAddingRequest: AddressAddingRequest): Completable
}

class LoadAddressesUseCaseImpl @Inject constructor(
    private val addressesService: AddressesService
) : LoadAddressesUseCase {
    override fun loadGeoSuggestions(query: String): Single<GeoSuggestionsList> =
        addressesService.loadGeoSuggestions(query)

    override fun addNewClientAddress(addressesAddingRequest: AddressAddingRequest): Completable =
        addressesService.addSelectedAddress(addressesAddingRequest)
}