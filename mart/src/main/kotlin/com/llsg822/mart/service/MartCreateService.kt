package com.llsg822.mart.service

import com.llsg822.common.IdResponse
import com.llsg822.mart.entity.MartEntity
import com.llsg822.mart.entity.MartId
import com.llsg822.mart.entity.MartOperationEntity
import com.llsg822.mart.event.CreateMartEvent
import com.llsg822.mart.repository.MartOperationRepository
import com.llsg822.mart.repository.MartRepository
import com.llsg822.mart.repository.MartSpatialRepository
import com.llsg822.mart.service.request.CreateMartRequest
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class MartCreateService(
    private val martRepository: MartRepository,
    private val martOperationRepository: MartOperationRepository,
    private val martSpatialRepository: MartSpatialRepository,
    private val applicationEventPublisher: ApplicationEventPublisher,
) {
    @Transactional
    fun createMart(request: CreateMartRequest): IdResponse<MartId> {
        val srid4326To5179 = martSpatialRepository.transform(
            latitude = request.latitude,
            longitude = request.longitude,
            srid = 5179
        )
        val mart = request.toMartEntity(
            x5179 = srid4326To5179.x,
            y5179 = srid4326To5179.y,
        )
        martRepository.save(mart)
        val martOperation = mart.createDefaultMartOperationEntity()
        martOperationRepository.save(martOperation)
        applicationEventPublisher.publishEvent(CreateMartEvent(martId = mart.id))
        return IdResponse(id = mart.id)
    }
}

fun CreateMartRequest.toMartEntity(
    x5179: Double,
    y5179: Double,
): MartEntity {
    return MartEntity.create(
        name = this.name,
        latitude = this.latitude,
        longitude = this.longitude,
        x5179 = x5179,
        y5179 = y5179,
    )
}

fun MartEntity.createDefaultMartOperationEntity(): MartOperationEntity {
    return MartOperationEntity.create(
        martId = this.id,
        isOpen = false,
    )
}