package com.llsg822.mart.api

import com.llsg822.common.IdResponse
import com.llsg822.common.ApiResponse
import com.llsg822.mart.entity.MartId
import com.llsg822.mart.service.response.GetMartOperationResponse
import com.llsg822.mart.service.MartOperationService
import com.llsg822.mart.service.request.UpdateMartOperationRequest
import com.llsg822.mart.utils.toSuccessResponse
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
@Tag(
    name = "mart (operation)",
    description = "마트 운영 여부에 대한 읽기, 업데이트를 제공합니다."
)
class MartOperationController(
    private val martOperationService: MartOperationService,
) {
    @GetMapping("/marts/{martId}/operation")
    @Operation(summary = "마트 운영 여부를 조회합니다.")
    fun getMartOperation(
        @PathVariable martId: MartId,
    ): ApiResponse.Success<GetMartOperationResponse> {
        val response = martOperationService.getMartOperation(martId)
        return response.toSuccessResponse()
    }

    @PutMapping("/marts/{martId}/operation")
    @Operation(summary = "마트 운영 여부를 수정합니다.")
    fun updateMartOperation(
        @PathVariable martId: MartId,
        @RequestBody request: UpdateMartOperationRequest,
    ): ApiResponse.Success<IdResponse<MartId>> {
        val response = martOperationService.updateMartOperation(
            martId = martId,
            request = request,
        )
        return response.toSuccessResponse()
    }
}