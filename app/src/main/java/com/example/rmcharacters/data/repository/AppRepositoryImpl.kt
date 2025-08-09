package com.example.rmcharacters.data.repository

import android.util.Log
import com.example.rmcharacters.data.local.mapper.toDomain
import com.example.rmcharacters.data.local.mapper.toEntity
import com.example.rmcharacters.data.local.source.CharacterLocalDataSource
import com.example.rmcharacters.data.remote.common.NetworkResult
import com.example.rmcharacters.data.remote.mapper.toDomainModel
import com.example.rmcharacters.data.remote.source.CharactersRemoteDataSource
import com.example.rmcharacters.domain.common.AppError
import com.example.rmcharacters.domain.common.Resource
import com.example.rmcharacters.domain.model.Character
import com.example.rmcharacters.domain.model.CharacterResponse
import com.example.rmcharacters.domain.model.Episode
import com.example.rmcharacters.domain.model.FilterParameters
import com.example.rmcharacters.domain.model.Info
import com.example.rmcharacters.domain.model.Location
import com.example.rmcharacters.domain.repository.AppRepository
import javax.inject.Inject

class AppRepositoryImpl @Inject constructor(
    private val remote: CharactersRemoteDataSource,
    private val local: CharacterLocalDataSource,
) : AppRepository {

    private var nextPage: String? = null
    private var filter = FilterParameters()

    override suspend fun getCharactersResponse(
        filterParameters: FilterParameters
    ): Resource<CharacterResponse> {
        filter = filterParameters
        val remoteResult = remote.getCharacters(
            filter.name,
            filter.status,
            filter.species,
            filter.type,
            filter.gender
        )
        return when (remoteResult) {
            is NetworkResult.Success-> {
                val result = remoteResult.data.toDomainModel()
                nextPage = remoteResult.data.info.next

                local.insertCharacterList(result.results.toEntity())
                Resource.Success(result)
            }

            is NetworkResult.Error -> {
                nextPage = "database"
                getCharactersFromDatabase(remoteResult.error)
            }
        }
    }

    override suspend fun getNextPage(): Resource<CharacterResponse> {
        nextPage?.let { url ->
            val remoteResult = remote.getNextPage(url)
            return when (remoteResult) {
                is NetworkResult.Success -> {
                    val result = remoteResult.data.toDomainModel()
                    local.insertCharacterList(result.results.toEntity())
                    nextPage = remoteResult.data.info.next

                    Resource.Success(result)
                }
                is NetworkResult.Error -> {
                    getCharactersFromDatabase(remoteResult.error)
                }
            }
        }
        return Resource.Empty
    }

    override suspend fun getCharacter(id: Int): Resource<Character> {
        val remoteResult = remote.getCharacter(id)
        when (remoteResult) {
            is NetworkResult.Success -> {
                val result = remoteResult.data.toDomainModel()
                local.insertCharacter(result.toEntity())
                return Resource.Success(result)
            }

            is NetworkResult.Error -> {
                return try {
                    val localResult = local.getCharacterById(id)
                    if (localResult != null) {
                        Log.d("DB_CHECK", "Персонаж найден в БД: ${localResult.name}")
                        Resource.PartialSuccess(
                            localResult.toDomain(),
                            remoteResult.error
                        )
                    } else {
                        Log.d("DB_CHECK", "Персонаж с id=$id отсутствует в БД")
                        Resource.Error(remoteResult.error)
                    }
                } catch (_: Exception) {
                    Resource.Error(AppError.DatabaseError)
                }
            }
        }
    }

    override suspend fun getLocations(idList: List<Int>): Resource<List<Location>> {
        return try {
            val localResult = local.getLocationsById(idList)
            val missingIds = idList.filter { id -> localResult.none { it.id == id } }

            if (missingIds.isEmpty()) {
                return Resource.Success(localResult.map { it.toDomain() })
            } else {
                val remoteResult = remote.getLocations(missingIds)
                when (remoteResult) {
                    is NetworkResult.Success -> {
                        val newLocations = remoteResult.data.map { it.toDomainModel() }
                        local.insertLocations(newLocations.map { it.toEntity() })
                        val combinedLocations = localResult.map { it.toDomain() } + newLocations
                        return Resource.Success(combinedLocations)
                    }
                    is NetworkResult.Error -> {
                        val message = remoteResult.error
                        return if (localResult.isNotEmpty()) {
                            nextPage = null
                            Resource.PartialSuccess(
                                localResult.map { it.toDomain() },
                                message
                            )
                        } else {
                            Resource.Error(message)
                        }
                    }
                }
            }
        } catch (_: Exception) {
            Resource.Error(AppError.DatabaseError)
        }
    }

    override suspend fun getEpisodes(idList: List<Int>): Resource<List<Episode>> {
        return try {
            val localResult = local.getEpisodesByIds(idList)
            val missingIds = idList.filter { id -> localResult.none { it.id == id } }

            if (missingIds.isEmpty()) {
                Resource.Success(localResult.map { it.toDomain() })
            } else {
                val remoteResult = remote.getEpisodes(missingIds)
                when (remoteResult) {
                    is NetworkResult.Success -> {
                        val newEpisodes = remoteResult.data.map { it.toDomainModel() }
                        local.insertEpisodes(newEpisodes.map { it.toEntity() })

                        val combinedEpisodes = localResult.map { it.toDomain() } + newEpisodes
                        Resource.Success(combinedEpisodes)
                    }
                    is NetworkResult.Error -> {
                        val message = remoteResult.error
                        if (localResult.isNotEmpty()) {
                            Resource.PartialSuccess(
                                localResult.map { it.toDomain() },
                                message
                            )
                        } else {
                            Resource.Error(message)
                        }
                    }
                }
            }
        } catch (_: Exception) {
            Resource.Error(AppError.DatabaseError)
        }
    }

    private suspend fun getCharactersFromDatabase(appError: AppError): Resource<CharacterResponse> {
        return try {
            val localResult = local.getCharacterList(
                filter.name,
                filter.status,
                filter.species,
                filter.type,
                filter.gender
            )
            if (localResult.isNotEmpty()) {
                val result = CharacterResponse(
                    Info(localResult.size, localResult.size / 20),
                    localResult.toDomain()
                )
                Resource.PartialSuccess(result, appError)
            } else {
                Resource.Error(appError)
            }
        } catch (_: Exception) {
            Resource.Error(AppError.DatabaseError)
        }
    }
}