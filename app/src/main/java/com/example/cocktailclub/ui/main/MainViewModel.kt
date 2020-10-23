package com.example.cocktailclub.ui.main

import android.app.Application
import androidx.lifecycle.ViewModel
import com.example.cocktailclub.repository.db.CocktailsRoom
import com.example.cocktailclub.repository.network.ApiResponseModel
import com.example.cocktailclub.repository.network.CocktailDBApiService
import com.example.cocktailclub.repository.network.DrinksItem
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel(private val context: Application) : ViewModel() {
    // Listado de cocktails
    private lateinit var cocktailsList: List<DrinksItem>


    /**
     * STATIC INIT
     */

    companion object {
        const val API_KEY: String = "a"
    }


    /**
     * PUBLIC FUNCTIONS
     */

    fun getCocktailsList(callback: CocktailDBApiService.CallbackResponse<List<DrinksItem>>) {
        // Encolamos el servicio Api para recuperar la lista de cocktails
        CocktailDBApiService().allCocktailsApiService.getCocktailsList(API_KEY).enqueue(object : Callback<ApiResponseModel> {
            // Cuando el servicio Api AllCocktailsApi devuelve una respuesta correcta
            override fun onResponse(call: Call<ApiResponseModel>, response: Response<ApiResponseModel>) {
                // Comprobamos el 200 y que haya contenido en la respuesta para devolverla a la vista
                if (response.isSuccessful && response.body() != null) {
                    val apiResponse = response.body()!!
                    cocktailsList = apiResponse.drinks as List<DrinksItem>
                    cocktailsList.let { callback.onResponse(it) }

                } else {
                    callback.onFailure(Throwable(response.message()), response)
                }
            }

            // Cuando el servicio Api AllCocktailsApi devuelve una respuesta error
            override fun onFailure(call: Call<ApiResponseModel>, t: Throwable) {
                callback.onFailure(t)
            }
        })
    }

    fun checkLocalDB(remoteCocktailsList: List<DrinksItem>) : List<DrinksItem> {
        // Conseguimos una lista de cocktails definitiva conforme a si existe la BD en Room
        val roomCocktailsList = CocktailsRoom.getDBInstance(context).dataAccessObject().selectCocktails()
        if (roomCocktailsList.isEmpty()) {
            cocktailsList = remoteCocktailsList
            saveCocktails(remoteCocktailsList)
        } else {
            cocktailsList = roomCocktailsList
        }
        return cocktailsList
    }


    /**
     * PRIVATE FUNCTIONS
     */

    // Accede a Room para guardar el cocktail como favorito
    private fun saveCocktails(cocktailsList: List<DrinksItem>) {
        cocktailsList.map { item ->
            // Insertamos el cocktail en la tabla de favoritos
            CocktailsRoom.getDBInstance(context).dataAccessObject().insertCocktail(item)
        }
    }
}