package com.example.rmcharacters.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.rmcharacters.data.local.entity.CharacterListEntity
import com.example.rmcharacters.data.local.entity.EpisodeEntity
import com.example.rmcharacters.data.local.entity.LocationEntity

@Dao
interface CharactersDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCharacterList(characters: List<CharacterListEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCharacter(character: CharacterListEntity)

    @Query("""
        SELECT * FROM character_list
        WHERE (:name IS NULL OR name LIKE '%' || :name || '%')
        AND (:status IS NULL OR status LIKE '%' || :status || '%')
        AND (:species IS NULL OR species LIKE '%' || :species || '%')
        AND (:type IS NULL OR type LIKE '%' || :type || '%')
        AND (:gender IS NULL OR gender LIKE '%' || :gender || '%')
    """)
    suspend fun getFilteredCharactersFromList(
        name: String? = null,
        status: String? = null,
        species: String? = null,
        type: String?,
        gender: String? = null
    ): List<CharacterListEntity>

    @Query("DELETE FROM character_list")
    suspend fun deleteAllCharactersFromList()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEpisodes(episodes: List<EpisodeEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLocations(locations: List<LocationEntity>)

    @Query("SELECT * FROM locations WHERE id IN (:locationIds)")
    suspend fun getLocationById(locationIds: List<Int>): List<LocationEntity>

    @Query("SELECT * FROM episodes WHERE id IN (:episodeIds)")
    suspend fun getEpisodesByIds(episodeIds: List<Int>): List<EpisodeEntity>

    @Query("SELECT * FROM character_list WHERE id IN (:characterId)")
    suspend fun getCharacter(characterId: Int): CharacterListEntity
}