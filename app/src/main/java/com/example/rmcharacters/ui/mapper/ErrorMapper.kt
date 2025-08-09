package com.example.rmcharacters.ui.mapper


import com.example.rmcharacters.R
import com.example.rmcharacters.domain.common.AppError
import com.example.rmcharacters.ui.model.UiInfoState
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ErrorMapper @Inject constructor() {

    fun map(error: AppError): UiInfoState {
        return when (error) {
            is AppError.NetworkError -> UiInfoState(
                icon = R.drawable.ic_network_error,
                title = R.string.connection_error,
                subtitle = R.string.connection_error_description
            )
            is AppError.ApiError -> when (error.code) {
                404 -> UiInfoState(
                    icon = R.drawable.ic_not_found_error,
                    title = R.string.not_found_error,
                    subtitle = R.string.not_found_error_description
                )
                else -> UiInfoState(
                    icon = R.drawable.ic_api_error,
                    title = R.string.api_error,
                    subtitle = R.string.api_error_description
                )
            }
            is AppError.DatabaseError -> UiInfoState(
                icon = R.drawable.ic_database_error,
                title = R.string.database_error,
                subtitle = R.string.database_error_description
            )
            is AppError.UnknownError -> UiInfoState(
                icon = R.drawable.ic_unknown_error,
                title = R.string.something_error,
                subtitle = R.string.something_error_description
            )
        }
    }
}