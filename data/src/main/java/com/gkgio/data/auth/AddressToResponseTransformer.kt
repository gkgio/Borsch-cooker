package com.gkgio.data.auth

import com.gkgio.data.BaseTransformer
import com.gkgio.data.address.adding.AddressResponse
import com.gkgio.data.address.adding.CoordinatesToResponseTransformer
import com.gkgio.domain.address.Address
import javax.inject.Inject

class AddressToResponseTransformer @Inject constructor(
    private val coordinatesResponseTransformer: CoordinatesToResponseTransformer
) : BaseTransformer<Address, AddressResponse> {

    override fun transform(data: Address) = with(data) {
        AddressResponse(
            id,
            city,
            country,
            flat,
            house,
            coordinatesResponseTransformer.transform(location),
            street,
            block
        )
    }
}