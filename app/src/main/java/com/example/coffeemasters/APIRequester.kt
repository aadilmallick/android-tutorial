package com.example.coffeemasters

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.*
import okhttp3.OkHttpClient
import okhttp3.Request

data class Todo(
    val userId: Int,
    val id: Int,
    val title: String,
    val completed: Boolean
)
class APIRequesterObjects<T>(public var url: String, private var TClass: Class<T>) {
    private val client = OkHttpClient()

    private val request = Request.Builder()
        .url(url)
        .build()

    suspend fun getData(): T? {
        val response = withContext(Dispatchers.IO) {
            client.newCall(request).execute()
        }

        val responseData = response.body?.string()
        if (responseData == null) {
            return null
        }

        // Deserialize JSON string using Gson
        val gson = Gson()
        return gson.fromJson(responseData, TClass) as T
    }
}

class APIRequesterArrays<T>(
    private val url: String,
    private val typeToken: TypeToken<List<T>>
) {

    companion object {
        fun <T> createClient(url: String, TClass: Class<T>) : APIRequesterArrays<T> {
            val typeToken = object : TypeToken<List<T>>() {}
            return APIRequesterArrays<T>(
                url = url,
                typeToken = typeToken
            )
        }
    }
    private val client = OkHttpClient()

    private val request = Request.Builder()
        .url(url)
        .build()

    suspend fun getData(): List<T>? {
        val response = withContext(Dispatchers.IO) {
            client.newCall(request).execute()
        }

        val responseData = response.body?.string()
        if (responseData == null) {
            return null
        }

        // Deserialize JSON string using Gson
        val gson = Gson()
        return gson.fromJson(responseData, typeToken.type)
    }
}

