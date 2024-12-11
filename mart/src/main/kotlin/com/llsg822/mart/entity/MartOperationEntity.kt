package com.llsg822.mart.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "mart_operation")
class MartOperationEntity private constructor(
    @Id
    @Column(name = "mart_id", nullable = false)
    val martId: MartId,
    @Column(name = "is_open", nullable = false)
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