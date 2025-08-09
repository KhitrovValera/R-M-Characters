package com.example.rmcharacters.domain.model

data class Character(
    val id: Int,
    val name: String,
    val status: String,
    val species: String,
    val type: String,
    val gender: String,
    val originName: String,
    val originId: Int,
    val lastLocationName: String,
    val lastLocationId: Int,
    val image: String,
    val episodeIdList: List<Int>,
    val lastUpdated: Long
)
