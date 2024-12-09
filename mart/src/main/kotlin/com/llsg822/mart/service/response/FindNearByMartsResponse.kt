package com.llsg822.mart.service.response

data class FindNearByMartsResponse(
    val mart: GetMartResponse,
    val distance: Double,
)