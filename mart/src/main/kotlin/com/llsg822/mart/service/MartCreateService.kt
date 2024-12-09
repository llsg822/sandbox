package com.llsg822.mart.service

import com.llsg822.common.IdResponse
import com.llsg822.mart.entity.MartEntity
import com.llsg822.mart.entity.MartId
import com.llsg822.mart.entity.MartOperationEntity
import com.llsg822.mart.event.CreateMartEvent
import com.llsg822.mart.repository.MartOperationRepository
import com.llsg822.mart.repository.MartRepository
import com.llsg822.mart.service.request.CreateMartRequest
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class MartCreateService(
    private val martRepository: MartRepository,
    private val martOperationRepository: MartOperationRepository,
    private val applicationEventPublisher: ApplicationEventPublisher,
) {
    @Transactional
    fun createMart(request: CreateMartRequest): IdResponse<MartId> {
        val mart = request.toMartEntity()
        martRepository.save(mart)
        val martOperation = mart.createDefaultMartOperationEntity()
        martOperationRepository.save(martOperation)
        applicationEventPublisher.publishEvent(CreateMartEvent(martId = mart.id))
        return IdResponse(id = mart.id)
    }
}

fun CreateMartRequest.toMartEntity(): MartEntity {
    return MartEntity.create(
        name = this.name,
        latitude = this.latitude,
        longitude = this.longitude,
    )
}

fun MartEntity.createDefaultMartOperationEntity(): MartOperationEntity {
    return MartOperationEntity.create(
        martId = this.id,
        isOpen = false,
    )
}