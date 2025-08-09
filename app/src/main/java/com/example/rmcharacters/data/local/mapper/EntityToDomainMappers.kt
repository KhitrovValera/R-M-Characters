package com.example.rmcharacters.data.local.mapper

import com.example.rmcharacters.data.local.entity.CharacterListEntity
import com.example.rmcharacters.data.local.entity.EpisodeEntity
import com.example.rmcharacters.data.local.entity.LocationEntity
import com.example.rmcharacters.domain.model.Character
import com.example.rmcharacters.domain.model.Episode
import com.example.rmcharacters.domain.model.Location

fun CharacterListEntity.toDomain(): Character {
    return Character(
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

fun List<CharacterListEntity>.toDomain(): List<Character> {
    return this.map { it -> it.toDomain() }
}

fun LocationEntity.toDomain(): Location {
    return Location(
        id = id,
        name = name,
        type = type,
        dimension = dimension
    )
}

fun EpisodeEntity.toDomain(): Episode {
    return Episode(
        id = id,
        name = name,
        airDate = airDate,
        episode = episode
    )
}