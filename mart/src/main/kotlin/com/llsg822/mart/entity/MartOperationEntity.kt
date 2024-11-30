package com.llsg822.mart.entity

import jakarta.persistence.Entity
import jakarta.persistence.Id

@Entity
class MartOperationEntity private constructor(
    @Id
    val martId: MartId,
    var isOpen: Boolean,
) {
    fun updateIsOpen(isOpen: Boolean) {
        this.isOpen = isOpen
    }

    companion object {
        fun create(
            martId: Long,
            isOpen: Boolean,
        ): MartOperationEntity {
            return MartOperationEntity(
                martId = martId,
                isOpen = isOpen,
            )
        }
    }
}