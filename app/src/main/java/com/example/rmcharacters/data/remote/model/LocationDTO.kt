package com.example.rmcharacters.data.remote.model

import kotlinx.serialization.Serializable

@Serializable
data class LocationDTO(
    val id: Int,
    val name: String,
    val type: String,
    val dimension: String,
    val residents: List<String>,
    val url: String,
    val created: String
)