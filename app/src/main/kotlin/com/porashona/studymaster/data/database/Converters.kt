package com.porashona.studymaster.data.database

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.porashona.studymaster.data.model.SessionType
import com.porashona.studymaster.data.model.RepeatType
import java.util.Date

class Converters {

    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return value?.let { Date(it) }
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time
    }

    @TypeConverter
    fun fromSessionType(value: SessionType): String {
        return value.name
    }

    @TypeConverter
    fun toSessionType(value: String): SessionType {
        return SessionType.valueOf(value)
    }

    @TypeConverter
    fun fromRepeatType(value: RepeatType): String {
        return value.name
    }

    @TypeConverter
    fun toRepeatType(value: String): RepeatType {
        return RepeatType.valueOf(value)
    }

    @TypeConverter
    fun fromIntList(value: List<Int>): String {
        return Gson().toJson(value)
    }

    @TypeConverter
    fun toIntList(value: String): List<Int> {
        val listType = object : TypeToken<List<Int>>() {}.type
        return Gson().fromJson(value, listType) ?: emptyList()
    }
}