package com.example.mvvm_hilt.entity

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

/**
 * describe:使用Room框架,room不支持对象中直接存储集合
 * https://stackoverflow.com/questions/53812636/android-error-cannot-figure-out-how-to-save-this-field-into-database-you-can
 */
class TypeResponseConverter {

    @TypeConverter
    fun stringToSomeObjectList(data: String?): List<PokemonInfo.TypeResponse> {
        if (data == null) {
            return emptyList()
        }

        val listType = object : TypeToken<List<PokemonInfo.TypeResponse>>() {}.type

        return Gson().fromJson(data, listType)
    }

    @TypeConverter
    fun someObjectListToString(someObjects: List<PokemonInfo.TypeResponse>): String {
        return Gson().toJson(someObjects)
    }

}