package com.llsg822.common

sealed class ApiResponse<T> {
    data class Success<T>(
        val payload: T,
    ) : ApiResponse<T>()

    data class Paged<T>(
        val payload: List<T>,
        val metadata: PageMetadata
    ) {
        data class PageMetadata(
            val currentPage: Int,
            val currentSize: Int,
            val totalCount: Long,
        )
    }

    data class Failure<T>(
        val code: String,
        val message: String,
        val payload: T,
    ) : ApiResponse<T>()
}