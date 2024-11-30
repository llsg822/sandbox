package com.llsg822.mart.service.response

import com.llsg822.mart.entity.MartId

data class GetMartOperationResponse(
    val martId: MartId,
    val isOpen: Boolean,
)