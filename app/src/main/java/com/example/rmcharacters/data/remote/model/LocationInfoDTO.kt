package com.example.rmcharacters.data.remote.model

import kotlinx.serialization.Serializable

@Serializable
data class LocationInfoDTO(
    val name: String,
    val url: String
)
