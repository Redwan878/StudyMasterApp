package com.porashona.studymaster.data.database

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.porashona.studymaster.data.model.*
import java.util.Date

class Converters {

    private val gson = Gson()

    // Date Converters
    @TypeConverter
    fun fromTimestamp(value: Long?): Date? = value?.let { Date(it) }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? = date?.time

    // SessionType
    @TypeConverter
    fun fromSessionType(value: SessionType): String = value.name

    @TypeConverter
    fun toSessionType(value: String): SessionType = SessionType.valueOf(value)

    // RepeatType
    @TypeConverter
    fun fromRepeatType(value: RepeatType): String = value.name

    @TypeConverter
    fun toRepeatType(value: String): RepeatType = RepeatType.valueOf(value)

    // GoalType
    @TypeConverter
    fun fromGoalType(value: GoalType): String = value.name

    @TypeConverter
    fun toGoalType(value: String): GoalType = GoalType.valueOf(value)

    // TaskPriority
    @TypeConverter
    fun fromTaskPriority(value: TaskPriority): String = value.name

    @TypeConverter
    fun toTaskPriority(value: String): TaskPriority = TaskPriority.valueOf(value)

    // RecurringType
    @TypeConverter
    fun fromRecurringType(value: RecurringType): String = value.name

    @TypeConverter
    fun toRecurringType(value: String): RecurringType = RecurringType.valueOf(value)

    // ChallengeType
    @TypeConverter
    fun fromChallengeType(value: ChallengeType): String = value.name

    @TypeConverter
    fun toChallengeType(value: String): ChallengeType = ChallengeType.valueOf(value)

    // ResourceType
    @TypeConverter
    fun fromResourceType(value: ResourceType): String = value.name

    @TypeConverter
    fun toResourceType(value: String): ResourceType = ResourceType.valueOf(value)

    // EventType
    @TypeConverter
    fun fromEventType(value: EventType): String = value.name

    @TypeConverter
    fun toEventType(value: String): EventType = EventType.valueOf(value)

    // List<Int> for repeat days
    @TypeConverter
    fun fromIntList(value: List<Int>): String = gson.toJson(value)

    @TypeConverter
    fun toIntList(value: String): List<Int> {
        val listType = object : TypeToken<List<Int>>() {}.type
        return gson.fromJson(value, listType) ?: emptyList()
    }

    // List<String> for tags, image paths, etc.
    @TypeConverter
    fun fromStringList(value: List<String>): String = gson.toJson(value)

    @TypeConverter
    fun toStringList(value: String): List<String> {
        val listType = object : TypeToken<List<String>>() {}.type
        return gson.fromJson(value, listType) ?: emptyList()
    }
}