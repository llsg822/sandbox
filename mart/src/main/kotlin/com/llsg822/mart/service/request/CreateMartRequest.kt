package com.llsg822.mart.service.request

data class CreateMartRequest(
    val name: String,
    val latitude: Double,
    val longitude: Double,
)