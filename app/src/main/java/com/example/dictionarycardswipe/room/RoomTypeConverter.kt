package com.example.dictionarycardswipe.room

import androidx.room.TypeConverter
import com.example.dictionarycardswipe.models.Model
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class RoomTypeConverter {
    var gson = Gson()

    @TypeConverter
    fun acronymsToString(beerModel: Model): String = gson.toJson(beerModel)

    @TypeConverter
    fun stringToAcronyms(data: String): Model {
        val listType = object : TypeToken<Model>() {}.type
        return gson.fromJson(data, listType)
    }
}