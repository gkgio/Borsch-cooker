package com.gkgio.data.address.adding

import com.gkgio.data.BaseTransformer
import com.gkgio.domain.address.Coordinates
import javax.inject.Inject

class CoordinatesToResponseTransformer @Inject constructor() :
    BaseTransformer<Coordinates, CoordinatesResponse> {

    override fun transform(data: Coordinates) = with(data) {
        CoordinatesResponse(
            latitude,
            longitude
        )
    }
}