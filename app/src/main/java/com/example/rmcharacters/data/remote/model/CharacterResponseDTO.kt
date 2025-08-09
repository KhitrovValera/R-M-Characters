package com.example.rmcharacters.data.remote.model

import kotlinx.serialization.Serializable

@Serializable
data class CharacterResponseDTO(
    val info: InfoDTO,
    val results: List<CharacterDTO>
)