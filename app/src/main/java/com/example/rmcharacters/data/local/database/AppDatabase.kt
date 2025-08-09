package com.example.rmcharacters.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.rmcharacters.data.local.common.IntListConverter
import com.example.rmcharacters.data.local.dao.CharactersDao
import com.example.rmcharacters.data.local.entity.CharacterListEntity
import com.example.rmcharacters.data.local.entity.EpisodeEntity
import com.example.rmcharacters.data.local.entity.LocationEntity

@Database(
    entities = [
        CharacterListEntity::class,
        LocationEntity::class,
        EpisodeEntity::class
    ],
    version = 1,
    exportSchema = false
)
@TypeConverters(IntListConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun charactersDao(): CharactersDao
}
