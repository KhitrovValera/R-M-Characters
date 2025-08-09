package com.example.rmcharacters.data.remote.common

import com.example.rmcharacters.domain.common.AppError
import retrofit2.HttpException
import java.io.IOException

class ApiCaller {
    suspend fun <T> safeApiCall(call: suspend () -> T): NetworkResult<T> {
        return try {
            NetworkResult.Success(call())
        } catch (e: HttpException) {
            NetworkResult.Error(AppError.ApiError(e.code()))
        } catch (_: IOException) {
            NetworkResult.Error(AppError.NetworkError)
        } catch (_: Exception) {
            NetworkResult.Error(AppError.UnknownError)
        }
    }
}