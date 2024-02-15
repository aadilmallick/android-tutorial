package com.example.coffeemasters


import android.util.Log
import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import kotlin.reflect.KSuspendFunction0

interface CoffeeMastersApiService {
    @GET("menu.json")
    suspend fun fetchMenu(): Response<List<Category>>
}

object CoffeeMastersAPI {
    // 1. build retrofit instance
    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl("https://firtman.github.io/coffeemasters/api/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    // 2. create retrofit instance lazily
    val menuService: CoffeeMastersApiService by lazy {
        retrofit.create(CoffeeMastersApiService::class.java)
    }

    suspend fun <T> fetchFunction(func: KSuspendFunction0<Response<T>>): T? {
        return try {
            val response = func()
            if (response.isSuccessful) {
                response.body()!!
            } else {
                Log.e("CoffeeMastersAPI", "fetchData: ${response.errorBody()}")
                null
            }
        } catch (e: Exception) {
            Log.e("CoffeeMastersAPI", "fetchData: ${e.message}")
            null
        }
    }

    suspend fun fetchData(): List<Category>? {
        return fetchFunction<List<Category>>(menuService::fetchMenu)
    }
}

class DataManagerViewModel : ViewModel() {
    var menu = mutableStateOf(listOf<Category>())
    var cart = mutableStateOf(listOf<ItemInCart>())

    init {
        fetchMenu()
    }

    private fun fetchMenu() {
        viewModelScope.launch(Dispatchers.IO) {
            val data = CoffeeMastersAPI.fetchData()
            withContext(Dispatchers.Main) {
                if (data != null) {
                    menu.value = data
                }
            }
        }
    }

    fun cartAdd(product: Product) {
        var found = false
        cart.value.forEach {
            if (product.id == it.product.id) {
                it.quantity++
                found = true
            }
        }
        // IMPORTANT: it's a state, we have to change the whole list, not mutate its contents
        if (!found) {
            cart.value = listOf(*cart.value.toTypedArray(), ItemInCart(product, 1))
        }
    }

    fun cartClear() {
        cart.value = listOf()
    }

    fun cartRemove(product: Product) {
        cart.value = cart.value.filter { it.product.id != product.id }
    }

    fun cartTotal(): Double {
        var total = 0.0
        cart.value.forEach {
            total += it.product.price * it.quantity
        }
        return total
    }
}