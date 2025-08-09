package com.example.rmcharacters.data.local.common

import androidx.room.TypeConverter
import kotlinx.serialization.decodeFromString as decode // иначе не хочет работь. Всё перепробовал
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class IntListConverter {
    @TypeConverter
    fun fromIntList(value: List<Int>?): String? {
        return value?.let { Json.encodeToString(it) }
    }

    @TypeConverter
    fun toIntList(value: String?): List<Int>? {
        return value?.let { Json.decode(it) }
    }
}