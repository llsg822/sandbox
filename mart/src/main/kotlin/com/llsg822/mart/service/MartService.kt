package com.llsg822.mart.service

import com.llsg822.common.IdResponse
import com.llsg822.mart.entity.MartEntity
import com.llsg822.mart.entity.MartId
import com.llsg822.mart.event.UpdateMartEvent
import com.llsg822.mart.repository.MartRepository
import com.llsg822.mart.service.request.UpdateMartRequest
import com.llsg822.mart.service.response.GetMartResponse
import org.springframework.context.ApplicationEventPublisher
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class MartService(
    private val martRepository: MartRepository,
    private val applicationEventPublisher: ApplicationEventPublisher,
) {
    @Transactional(readOnly = true)
    fun getMart(martId: MartId): GetMartResponse {
        val mart = martRepository.findByIdOrNull(martId)
            ?: throw NoSuchElementException()
        return mart.toResponse()
    }

    @Transactional
    fun updateMart(
        martId: MartId,
        request: UpdateMartRequest,
    ): IdResponse<MartId> {
        val mart = martRepository.findByIdOrNull(martId)
            ?: throw NoSuchElementException()
        mart.updateName(request.name)
        applicationEventPublisher.publishEvent(UpdateMartEvent(martId = mart.id))
        return IdResponse(id = mart.id)
    }
}

fun MartEntity.toResponse(): GetMartResponse {
    return GetMartResponse(
        id = this.id,
        name = this.name,
        latitude = this.coordinate4326.y,
        longitude = this.coordinate4326.x,
    )
}
