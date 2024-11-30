package com.llsg822.mart.repository

import com.llsg822.mart.entity.MartId
import com.llsg822.mart.entity.MartOperationEntity
import org.springframework.data.jpa.repository.JpaRepository

interface MartOperationRepository: JpaRepository<MartOperationEntity, MartId>