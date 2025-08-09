package com.example.rmcharacters.data.remote.common

import com.example.rmcharacters.domain.common.AppError

sealed class NetworkResult<out T> {
    data class Success<T>(val data: T): NetworkResult<T>()
    data class Error(val error: AppError): NetworkResult<Nothing>()
}