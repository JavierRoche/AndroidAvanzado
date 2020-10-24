package com.example.cocktailclub.ui.detail

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.cocktailclub.R
import com.example.cocktailclub.common.CustomViewModel
import com.example.cocktailclub.repository.network.DrinksItem
import com.example.cocktailclub.ui.main.MainFragment
import kotlinx.android.synthetic.main.detail_activity.*


@Suppress("NAME_SHADOWING")
class DetailActivity: AppCompatActivity() {
    // Instancia del viewModel
    private val viewmodel: DetailViewModel by lazy {
        val factory = CustomViewModel(application)
        ViewModelProvider(this, factory).get(DetailViewModel::class.java)
    }


    /**
     * STATIC INIT
     */

    companion object {
        const val OBJECT_SERIALIZABLE = "EXTRA_OBJECT_SERIALIZABLE"
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Indicamos el fichero xml que maneja la actividad
        setContentView(R.layout.detail_activity)

        // Intentamos recuperar el cocktail seleccionado
        intent.let {
            val cocktail: DrinksItem? = it.extras?.getSerializable(OBJECT_SERIALIZABLE) as DrinksItem?
            cocktail?.let { it ->
                // Pintado de la vista mediante el viewmodel
                viewmodel.cocktail = it
                viewmodel.fillFields(cocktailImage, cocktailText, glassText,
                    categoryText, ingredientsText, instructionsText, favoriteButton,
                    this@DetailActivity)
            }
        }

        // User Interaction
        favoriteButton.setOnClickListener {
            viewmodel.updateCocktail(favoriteButton)
            finish()
        }
    }
}


