package com.example.rmcharacters.data.remote.source.impl

import com.example.rmcharacters.data.remote.api.CharactersService
import com.example.rmcharacters.data.remote.common.ApiCaller
import com.example.rmcharacters.data.remote.common.NetworkResult
import com.example.rmcharacters.data.remote.model.CharacterDTO
import com.example.rmcharacters.data.remote.model.CharacterResponseDTO
import com.example.rmcharacters.data.remote.model.EpisodeDTO
import com.example.rmcharacters.data.remote.model.LocationDTO
import com.example.rmcharacters.data.remote.source.CharactersRemoteDataSource
import javax.inject.Inject

class CharactersRemoteDataSourceImpl @Inject constructor(
    private val api: CharactersService,
    private val apiCaller: ApiCaller
) : CharactersRemoteDataSource {

    override suspend fun getCharacters(
        name: String?,
        status: String?,
        species: String?,
        type: String?,
        gender: String?
    ): NetworkResult<CharacterResponseDTO> {
        return apiCaller.safeApiCall { api.getCharacters(
            name,
            status,
            species,
            type,
            gender
        ) }
    }

    override suspend fun getNextPage(
        nextUrl: String
    ): NetworkResult<CharacterResponseDTO> {
        return apiCaller.safeApiCall { api.getNextPage(nextUrl) }
    }

    override suspend fun getCharacter(
        id: Int
    ): NetworkResult<CharacterDTO> {
        return apiCaller.safeApiCall { api.getCharacter(id) }
    }

    override suspend fun getLocations(
        idList: List<Int>
    ): NetworkResult<List<LocationDTO>> {
        return apiCaller.safeApiCall { api.getLocations(idList) }
    }

    override suspend fun getEpisodes(
        idList: List<Int>
    ): NetworkResult<List<EpisodeDTO>> {
        return apiCaller.safeApiCall { api.getEpisodes(idList) }
    }

}