package com.indie.apps.newyorktimestories.data.db

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.indie.apps.newyorktimestories.data.model.Multimedia
import java.util.Collections

class Converters {
    @TypeConverter
    fun stringToListMultimedia(data: String): List<Multimedia> {
        if (data == null) {
            return Collections.emptyList()
        }
        val listType = object :
            TypeToken<List<Multimedia?>?>() {}.type
        return Gson().fromJson<List<Multimedia>>(data, listType)
    }

    @TypeConverter
    fun listMultimediaToString(someObjects: List<Multimedia>): String {
        return Gson().toJson(someObjects)
    }
}