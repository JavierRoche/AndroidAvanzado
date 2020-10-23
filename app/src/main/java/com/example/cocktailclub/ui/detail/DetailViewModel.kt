package com.example.cocktailclub.ui.detail

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.ViewModel
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.cocktailclub.R
import com.example.cocktailclub.repository.db.CocktailsRoom
import com.example.cocktailclub.repository.network.DrinksItem
import com.google.android.material.floatingactionbutton.FloatingActionButton

@SuppressLint("SetTextI18n")
class DetailViewModel(private val context: Application) : ViewModel() {
    // Instancia del cocktail seleccionado
    lateinit var cocktail: DrinksItem

    /**
     * PUBLIC FUNCTIONS
     */

    // Rellena la informacion de pantalla
    fun fillFields(cocktailImage: ImageView, cocktailTextView: TextView, glassTextView: TextView,
                   categoryTextView: TextView, ingredientsTextView: TextView, instructionsTextView: TextView,
                   favoriteButton: FloatingActionButton, context: Context) {
        cocktail.let {
            // Fijamos la informacion del cocktail
            cocktailTextView.text = cocktail.strDrink
            glassTextView.text = cocktail.strGlass
            categoryTextView.text = "${cocktail.strAlcoholic} ${cocktail.strCategory}"
            instructionsTextView.text = "${instructionsTextView.text} ${cocktail.strInstructions}"
            formatIngredients(it, ingredientsTextView)

            // Fijamos la imagen recuperada en formato url con Glide
            Glide.with(context)
                .load(it.strDrinkThumb)
                .apply(RequestOptions().placeholder((R.drawable.ic_launcher_background)))
                .into(cocktailImage)

            if (isFavoriteCocktail()) {
                favoriteButton.setImageResource(R.drawable.ic_favorite_24)
            } else favoriteButton.setImageResource(R.drawable.ic_nofavorite_24)
        }
    }

    // Accede a Room para guardar el cocktail como favorito
    fun updateCocktail(favoriteButton: FloatingActionButton) {
        cocktail.let {
            // Cambiamos el valor del objeto y lo guardamos en Room
            it.bolFavorite = !it.bolFavorite
            val updatedCocktail = it
            // Sobreescribimos el cocktail en la tabla
            CocktailsRoom.getDBInstance(context).dataAccessObject().insertCocktail(updatedCocktail)
            // Fijamos el icono de favorito
            if (isFavoriteCocktail()) {
                favoriteButton.setImageResource(R.drawable.ic_favorite_24)
            } else favoriteButton.setImageResource(R.drawable.ic_nofavorite_24)
        }
    }


    /**
     * PRIVATE FUNCTIONS
     */


    private fun formatIngredients(cocktail: DrinksItem, ingredientsTextView: TextView) {
        val ingredients = arrayOf(cocktail.strIngredient1, cocktail.strIngredient2, cocktail.strIngredient3,
            cocktail.strIngredient4, cocktail.strIngredient5, cocktail.strIngredient6)
        val measures = arrayOf(cocktail.strMeasure1, cocktail.strMeasure2, cocktail.strMeasure3,
            cocktail.strMeasure4, cocktail.strMeasure5, cocktail.strMeasure6)


        for (ind in ingredients.indices) {
            ingredients[ind]?.let {
                ingredientsTextView.text = "${ingredientsTextView.text} ${ingredients[ind]}:  ${measures[ind]}\n"
            }
        }
    }

    private fun isFavoriteCocktail() : Boolean {
        cocktail.let { return it.bolFavorite }
    }
}