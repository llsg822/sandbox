package com.llsg822.mart.service.response

import com.llsg822.mart.entity.MartId

data class GetMartResponse(
    val id: MartId,
    val name: String,
    val latitude: Double,
    val longitude: Double,
)