package com.example.rmcharacters.data.remote.api

import com.example.rmcharacters.data.remote.model.CharacterDTO
import com.example.rmcharacters.data.remote.model.CharacterResponseDTO
import com.example.rmcharacters.data.remote.model.EpisodeDTO
import com.example.rmcharacters.data.remote.model.LocationDTO
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.Url

interface CharactersService {

    @GET("character")
    suspend fun getCharacters(
        @Query("name") name: String? = null,
        @Query("status") status: String? = null,
        @Query("species") species: String? = null,
        @Query("type") type: String? = null,
        @Query("gender") gender: String? = null
    ) : CharacterResponseDTO

    @GET
    suspend fun getNextPage(@Url nextUrl: String) : CharacterResponseDTO

    @GET("character/{id}")
    suspend fun getCharacter(
        @Path("id") id: Int
    ) : CharacterDTO

    @GET("location/{idList}")
    suspend fun getLocations(
        @Path("idList") id: List<Int>
    ) : List<LocationDTO>

    @GET("episode/{idList}")
    suspend fun getEpisodes(
        @Path("idList") id: List<Int>
    ) : List<EpisodeDTO>
}