package com.example.cocktailclub.repository.network

import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class CocktailDBApiService {
    // Elementos de comunicacion
    val allCocktailsApiService: AllCocktailsApi


    /**
     * DELEGATE PROTOCOL
     */

    interface CallbackResponse<T> {
        fun onResponse(response: T)
        fun onFailure(t: Throwable, res: Response<*>? = null)
    }


    /**
     * STATIC INIT
     */

    init {
        // Tiempo maximo de expiracion de la llamada que almacena el HttpClient
        val timeout: Long = 6 * 1000
        val client = OkHttpClient.Builder()
            .connectTimeout(timeout, TimeUnit.MILLISECONDS)
            .writeTimeout(timeout, TimeUnit.MILLISECONDS)
            .readTimeout(timeout, TimeUnit.MILLISECONDS)
            .build()

        // Elemento retrofit con el dominio y el seteo de Gson
        val retrofit = Retrofit.Builder()
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://www.thecocktaildb.com")
            .build()

        // Creacion de la comunicacion
        allCocktailsApiService = retrofit.create(AllCocktailsApi::class.java)
    }
}