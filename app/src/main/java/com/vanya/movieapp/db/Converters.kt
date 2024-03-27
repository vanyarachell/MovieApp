package com.vanya.movieapp.db

import androidx.room.TypeConverter
import com.google.gson.Gson

class Converters {
    @TypeConverter
    fun listToJson(genreIds: List<Int>?): String = Gson().toJson(genreIds)

    @TypeConverter
    fun jsonToList(value: String) =
        Gson().fromJson(value, Array<Int>::class.java).toList()
}