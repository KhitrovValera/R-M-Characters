package com.example.rmcharacters.data.local.source

import com.example.rmcharacters.data.local.entity.CharacterListEntity
import com.example.rmcharacters.data.local.entity.EpisodeEntity
import com.example.rmcharacters.data.local.entity.LocationEntity


interface CharacterLocalDataSource {

    suspend fun insertCharacterList(characters: List<CharacterListEntity>)

    suspend fun getCharacterList(
        name: String? = null,
        status: String? = null,
        species: String? = null,
        type: String? = null,
        gender: String? = null
    ): List<CharacterListEntity>

    suspend fun clearCharacterList()

    suspend fun insertCharacter(character: CharacterListEntity)

    suspend fun insertLocations(locations: List<LocationEntity>)

    suspend fun insertEpisodes(episodes: List<EpisodeEntity>)

    suspend fun getLocationsById(locationIds: List<Int>): List<LocationEntity>

    suspend fun getEpisodesByIds(episodeIds: List<Int>): List<EpisodeEntity>

    suspend fun getCharacterById(characterId: Int): CharacterListEntity?
}