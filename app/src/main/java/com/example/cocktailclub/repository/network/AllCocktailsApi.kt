package com.example.cocktailclub.repository.network

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

/**
 * ALL COCKTAILS API
 */

interface AllCocktailsApi {
    companion object {
        const val F_VALUE: String = "f"
    }

    @GET("api/json/v1/1/search.php")
    @Headers("Content-Type: application/json")

    fun getCocktailsList(@Query(F_VALUE) fValue: String): Call<ApiResponseModel>
}