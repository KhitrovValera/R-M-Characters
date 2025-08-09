package com.example.rmcharacters.data.local.source.impl

import com.example.rmcharacters.data.local.dao.CharactersDao
import com.example.rmcharacters.data.local.entity.CharacterListEntity
import com.example.rmcharacters.data.local.entity.EpisodeEntity
import com.example.rmcharacters.data.local.entity.LocationEntity
import com.example.rmcharacters.data.local.source.CharacterLocalDataSource
import javax.inject.Inject

class CharacterLocalDataSourceImpl @Inject constructor(
    private val charactersDao: CharactersDao
) : CharacterLocalDataSource {

    override suspend fun insertCharacterList(characters: List<CharacterListEntity>) {
        charactersDao.insertCharacterList(characters)
    }

    override suspend fun getCharacterList(
        name: String?,
        status: String?,
        species: String?,
        type: String?,
        gender: String?
    ): List<CharacterListEntity> {
        return charactersDao.getFilteredCharactersFromList(
            name,
            status,
            species,
            type,
            gender
        )
    }

    override suspend fun clearCharacterList() {
        charactersDao.deleteAllCharactersFromList()
    }

    override suspend fun insertCharacter(character: CharacterListEntity) {
        charactersDao.insertCharacter(character)
    }

    override suspend fun insertLocations(locations: List<LocationEntity>) {
        charactersDao.insertLocations(locations)
    }

    override suspend fun insertEpisodes(episodes: List<EpisodeEntity>) {
        charactersDao.insertEpisodes(episodes)
    }

    override suspend fun getLocationsById(locationIds: List<Int>): List<LocationEntity> {
        return charactersDao.getLocationById(locationIds)
    }

    override suspend fun getEpisodesByIds(episodeIds: List<Int>): List<EpisodeEntity> {
        return charactersDao.getEpisodesByIds(episodeIds)
    }

    override suspend fun getCharacterById(characterId: Int): CharacterListEntity? {
        return charactersDao.getCharacter(characterId)
    }
}