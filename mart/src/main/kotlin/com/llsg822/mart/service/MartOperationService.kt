package com.llsg822.mart.service

import com.llsg822.common.IdResponse
import com.llsg822.mart.entity.MartId
import com.llsg822.mart.entity.MartOperationEntity
import com.llsg822.mart.event.UpdateMartOperationEvent
import com.llsg822.mart.repository.MartOperationRepository
import com.llsg822.mart.service.request.UpdateMartOperationRequest
import com.llsg822.mart.service.response.GetMartOperationResponse
import org.springframework.context.ApplicationEventPublisher
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class MartOperationService(
    private val martOperationRepository: MartOperationRepository,
    private val applicationEventPublisher: ApplicationEventPublisher,
) {
    @Transactional(readOnly = true)
    fun getMartOperation(martId: MartId): GetMartOperationResponse {
        val martOperation = martOperationRepository.findByIdOrNull(martId)
            ?: throw NoSuchElementException()
        return martOperation.toResponse()
    }

    @Transactional
    fun updateMartOperation(
        martId: MartId,
        request: UpdateMartOperationRequest,
    ): IdResponse<MartId> {
        val martOperation = martOperationRepository.findByIdOrNull(martId)
            ?: throw NoSuchElementException()
        martOperation.updateIsOpen(request.isOpen)
        applicationEventPublisher.publishEvent(UpdateMartOperationEvent(martId = martId))
        return IdResponse(id = martOperation.martId)
    }
}

fun MartOperationEntity.toResponse(): GetMartOperationResponse {
    return GetMartOperationResponse(
        martId = this.martId,
        isOpen = this.isOpen,
    )
}