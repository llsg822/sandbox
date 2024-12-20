package com.llsg822.mart.service

import com.llsg822.mart.repository.MartSpatialRepository
import com.llsg822.mart.service.request.FindNearByMartsRequest
import com.llsg822.mart.service.response.FindNearByMartsResponse
import com.llsg822.mart.service.response.GetMartResponse
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class MartSpatialService(
    private val martSpatialRepository: MartSpatialRepository,
) {
    @Transactional(readOnly = true)
    fun findNearByMarts(
        request: FindNearByMartsRequest,
        pageable: Pageable,
    ): Page<FindNearByMartsResponse> {
        val marts = martSpatialRepository.findNearByMarts(
            latitude = request.latitude,
            longitude = request.longitude,
            pageable = pageable,
        )
        return marts.map {
            FindNearByMartsResponse(
                mart = GetMartResponse(
                    id = it.martId,
                    name = it.name,
                    latitude = it.latitude,
                    longitude = it.longitude,
                ),
                distance = it.distance,
            )
        }
    }
}

