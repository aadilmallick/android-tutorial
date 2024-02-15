package com.example.coffeemasters

import android.util.Log
import retrofit2.HttpException
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query
import java.io.IOException

object RetrofitInstance {
    val api: UserAPI by lazy {
        Retrofit.Builder()
            .baseUrl("https://jsonplaceholder.typicode.com/users")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(UserAPI::class.java)
    }

    suspend fun getUsers() : List<User>? {
        try {
            val response = api.getUsers()
            if (response.isSuccessful) {
                return response.body()
            }
        } catch (e: IOException) {
            Log.d("retrofit", "getUsers (IO error, no internet): ${e.message}")
            return null
        }
        catch (e: HttpException) {
            Log.d("retrofit", "getUsers (Bad Request): ${e.message}")
            return null
        }
        Log.d("retrofit", "getUsers (Unknown error)")
        return null
    }
}

interface UserAPI {
    @GET("/")
    suspend fun getUsers(): Response<List<User>>

    @POST("/")
    suspend fun createUser(@Body user: User): Response<User>
}