package com.example.coffeemasters

import java.io.Serializable



import com.google.gson.Gson
import java.lang.reflect.Type
data class ExampleClass<T>(val data: T)

class JSONConverter<T> {

    private val gson = Gson()

    // Convert an object of type T to a JSON string
    fun toJson(obj: T): String {
        return gson.toJson(obj)
    }

    // Convert a JSON string to an object of type T
    fun fromJson(json: String, clazz: Class<T>): T {
        return gson.fromJson(json, clazz)
    }

    // Convert a JSON string to an object of a generic type (e.g., List<T>)
    fun fromJson(json: String, type: Type): T {
        return gson.fromJson(json, type)
    }
}

