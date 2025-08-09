package com.example.rmcharacters.data.local.mapper

import com.example.rmcharacters.data.local.entity.CharacterListEntity
import com.example.rmcharacters.data.local.entity.EpisodeEntity
import com.example.rmcharacters.data.local.entity.LocationEntity
import com.example.rmcharacters.domain.model.Character
import com.example.rmcharacters.domain.model.Episode
import com.example.rmcharacters.domain.model.Location

fun Character.toEntity(): CharacterListEntity {
    return CharacterListEntity(
        id = id,
        name = name,
        status = status,
        species = species,
        type = type,
        gender = gender,
        originName = originName,
        originId = originId,
        lastLocationName = lastLocationName,
        lastLocationId = lastLocationId,
        image = image,
        episodeIdList = episodeIdList,
        lastUpdated = lastUpdated
    )
}

fun List<Character>.toEntity(): List<CharacterListEntity> {
    return this.map { it ->  it.toEntity() }
}

fun Location.toEntity(): LocationEntity {
    return LocationEntity(
        id = id,
        name = name,
        type = type,
        dimension = dimension
    )
}

fun Episode.toEntity(): EpisodeEntity {
    return EpisodeEntity(
        id = id,
        name = name,
        airDate = airDate,
        episode = episode
    )
}