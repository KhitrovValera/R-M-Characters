package com.example.rmcharacters.data.remote.mapper

import com.example.rmcharacters.data.remote.model.CharacterDTO
import com.example.rmcharacters.data.remote.model.CharacterResponseDTO
import com.example.rmcharacters.data.remote.model.EpisodeDTO
import com.example.rmcharacters.data.remote.model.InfoDTO
import com.example.rmcharacters.data.remote.model.LocationDTO
import com.example.rmcharacters.domain.model.Character
import com.example.rmcharacters.domain.model.CharacterResponse
import com.example.rmcharacters.domain.model.Episode
import com.example.rmcharacters.domain.model.Info
import com.example.rmcharacters.domain.model.Location

fun CharacterDTO.toDomainModel() = Character(
    id = id,
    name = name,
    status = status,
    species = species,
    type = type,
    gender = gender,
    originName = origin.name,
    originId = origin.url.extractIdFromUrl(),
    lastLocationName = location.name,
    lastLocationId = location.url.extractIdFromUrl(),
    image = image,
    episodeIdList = episode.map { it.extractIdFromUrl() },
    lastUpdated = System.currentTimeMillis()
)

fun String.extractIdFromUrl(): Int {
    val idString = this.substringAfterLast('/')
    return idString.toIntOrNull() ?: 0
}


fun InfoDTO.toDomainModel() = Info(
    count = count,
    pages = pages
)

fun CharacterResponseDTO.toDomainModel() = CharacterResponse(
    info = info.toDomainModel(),
    results = results.map { it.toDomainModel() }
)

fun LocationDTO.toDomainModel() = Location(
    id = id,
    name = name,
    type = type,
    dimension = dimension
)

fun EpisodeDTO.toDomainModel() = Episode(
    id = id,
    name = name,
    airDate = airDate,
    episode = episode
)