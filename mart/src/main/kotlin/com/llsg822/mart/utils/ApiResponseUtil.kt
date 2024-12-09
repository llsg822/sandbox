package com.llsg822.mart.utils

import com.llsg822.common.ApiResponse
import org.springframework.data.domain.Page

fun <T> Page<T>.toPagedResponse(): ApiResponse.Paged<T> {
    return ApiResponse.Paged(
        payload = this.content,
        metadata = ApiResponse.Paged.PageMetadata(
            currentPage = this.number,
            currentSize = this.size,
            totalCount = this.totalElements,
        )
    )
}

fun <T> T.toSuccessResponse(): ApiResponse.Success<T> {
    return ApiResponse.Success(
        payload = this,
    )
}