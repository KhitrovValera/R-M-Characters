package com.example.rmcharacters.data.remote.source

import com.example.rmcharacters.data.remote.common.NetworkResult
import com.example.rmcharacters.data.remote.model.CharacterDTO
import com.example.rmcharacters.data.remote.model.CharacterResponseDTO
import com.example.rmcharacters.data.remote.model.EpisodeDTO
import com.example.rmcharacters.data.remote.model.LocationDTO

interface CharactersRemoteDataSource {
    suspend fun getCharacters(
        name: String? = null,
        status: String? = null,
        species: String? = null,
        type: String? = null,
        gender: String? = null
    ): NetworkResult<CharacterResponseDTO>

    suspend fun getNextPage(
        nextUrl: String
    ): NetworkResult<CharacterResponseDTO>

    suspend fun getCharacter(
        id: Int
    ): NetworkResult<CharacterDTO>

    suspend fun getLocations(
        idList: List<Int>
    ): NetworkResult<List<LocationDTO>>

    suspend fun getEpisodes(
        idList: List<Int>
    ): NetworkResult<List<EpisodeDTO>>
}