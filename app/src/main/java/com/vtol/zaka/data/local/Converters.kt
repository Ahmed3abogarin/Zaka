package com.vtol.zaka.data.local

import androidx.room.TypeConverter
import org.json.JSONArray

class Converters {
    @TypeConverter
    fun fromList(options: List<String>): String = JSONArray(options).toString()

    @TypeConverter
    fun toList(json: String): List<String> {
        val array = JSONArray(json)
        return (0 until array.length()).map { array.getString(it) }
    }
}