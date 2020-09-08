package com.gkgio.domain.address

import io.reactivex.Completable
import io.reactivex.Single

interface AddressesService {
    fun loadGeoSuggestions(query: String): Single<GeoSuggestionsList>

    fun addSelectedAddress(addressAddingRequest: AddressAddingRequest): Completable
}