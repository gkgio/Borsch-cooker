package com.gkgio.borsch_cooker.own

import com.gkgio.data.BaseTransformer
import com.gkgio.domain.own.OwnDashboardDiagrammData
import javax.inject.Inject

class OwnDashboardDiagrammUiTransformer @Inject constructor() :
    BaseTransformer<OwnDashboardDiagrammData, OwnDashboardDiagrammUi> {
    override fun transform(data: OwnDashboardDiagrammData) = with(data) {
        OwnDashboardDiagrammUi(
            date,
            count
        )
    }

}