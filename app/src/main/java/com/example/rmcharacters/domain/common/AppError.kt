package com.example.rmcharacters.domain.common

sealed class AppError {
    object NetworkError : AppError()
    data class ApiError(val code: Int? = null) : AppError()
    object DatabaseError : AppError()
    object UnknownError : AppError()
}