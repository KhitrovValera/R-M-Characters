package com.example.rmcharacters.domain.repository

import com.example.rmcharacters.domain.common.Resource
import com.example.rmcharacters.domain.model.Character
import com.example.rmcharacters.domain.model.CharacterResponse
import com.example.rmcharacters.domain.model.Episode
import com.example.rmcharacters.domain.model.FilterParameters
import com.example.rmcharacters.domain.model.Location

interface AppRepository {

    suspend fun getCharactersResponse(
        filterParameters: FilterParameters
    ) : Resource<CharacterResponse>

    suspend fun getNextPage(): Resource<CharacterResponse>

    suspend fun getCharacter(
        id: Int
    ) : Resource<Character>

    suspend fun getLocations(
        idList: List<Int>
    ) : Resource<List<Location>>

    suspend fun getEpisodes(
        idList: List<Int>
    ) : Resource<List<Episode>>

}