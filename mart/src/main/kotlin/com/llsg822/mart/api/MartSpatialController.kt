package com.llsg822.mart.api

import com.llsg822.common.ApiResponse
import com.llsg822.mart.service.request.FindNearByMartsRequest
import com.llsg822.mart.service.response.FindNearByMartsResponse
import com.llsg822.mart.service.MartSpatialService
import com.llsg822.mart.utils.toPagedResponse
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.data.domain.PageRequest
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@Tag(
    name = "mart (spatial)",
    description = "마트의 지리적 특성을 활용한 조회 쿼리를 제공합니다."
)
class MartSpatialController(
    private val martSpatialService: MartSpatialService,
) {
    @GetMapping("/marts/nearby")
    @Operation(summary = "마트를 현재 좌표의 거리와 함께 조회합니다.")
    fun findNearByMarts(
        @RequestParam latitude: Double,
        @RequestParam longitude: Double,
        @RequestParam page: Int,
        @RequestParam size: Int,
    ): ApiResponse.Paged<FindNearByMartsResponse> {
        val request = FindNearByMartsRequest(
            latitude = latitude,
            longitude = longitude,
        )
        val pageable = PageRequest.of(page, size)
        val response = martSpatialService.findNearByMarts(
            request = request,
            pageable = pageable,
        )
        return response.toPagedResponse()
    }
}