package com.example.rmcharacters.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.rmcharacters.data.local.common.IntListConverter

@Entity(tableName = "character_list")
@TypeConverters(IntListConverter::class)
data class CharacterListEntity(
    @PrimaryKey val id: Int,
    val name: String,
    val status: String,
    val species: String,
    val type: String,
    val gender: String,
    val originName: String,
    val originId: Int,
    val lastLocationName: String,
    val lastLocationId: Int,
    val image: String,
    val episodeIdList: List<Int>,
    val lastUpdated: Long
)