package com.llsg822.mart.api

import com.llsg822.common.IdResponse
import com.llsg822.common.ApiResponse
import com.llsg822.mart.entity.MartId
import com.llsg822.mart.service.MartCreateService
import com.llsg822.mart.service.MartService
import com.llsg822.mart.service.request.CreateMartRequest
import com.llsg822.mart.service.request.UpdateMartRequest
import com.llsg822.mart.service.response.GetMartResponse
import com.llsg822.mart.utils.toSuccessResponse
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
@Tag(
    name = "mart",
    description = "마트에 대한 일반적인 CRUD를 제공합니다."
)
class MartController(
    private val martCreateService: MartCreateService,
    private val martService: MartService,
) {
    @GetMapping("/marts/{martId}")
    @Operation(summary = "마트를 조회합니다.")
    fun getMart(
        @PathVariable martId: MartId,
    ): ApiResponse.Success<GetMartResponse> {
        val response = martService.getMart(martId)
        return response.toSuccessResponse()
    }

    @PostMapping("/marts")
    @Operation(summary = "마트를 생성합니다.")
    fun createMart(
        @RequestBody request: CreateMartRequest,
    ): ApiResponse.Success<IdResponse<MartId>> {
        val response = martCreateService.createMart(request)
        return response.toSuccessResponse()
    }

    @PutMapping("/marts/{martId}")
    @Operation(summary = "마트를 수정합니다.")
    fun updateMart(
        @PathVariable martId: MartId,
        @RequestBody request: UpdateMartRequest,
    ): ApiResponse.Success<IdResponse<MartId>> {
        val response = martService.updateMart(
            martId = martId,
            request = request,
        )
        return response.toSuccessResponse()
    }
}