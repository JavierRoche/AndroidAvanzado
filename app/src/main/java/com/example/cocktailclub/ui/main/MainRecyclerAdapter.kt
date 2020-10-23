package com.example.cocktailclub.ui.main

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.cocktailclub.R
import com.example.cocktailclub.repository.network.DrinksItem
import kotlinx.android.synthetic.main.view_cell.view.*

class MainRecyclerAdapter(private val context: Context,
                          private val selectedCellDelegate: TapDelegate,
                          private var cocktailsList: List<DrinksItem>) : RecyclerView.Adapter<MainRecyclerAdapter.MainCellHolder>() {
    /**
     * PROTOCOL
     */

    interface TapDelegate {
        fun onItemTap(cocktail: DrinksItem?)
    }


    /**
     * LIFE CYCLE
     */

    // Devolvera el ViewHolder plantilla para cada elemento
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainCellHolder {
        // Le pasamos el recurso xml al que tiene que acceder como la vista que contendra cada celda
        val view = LayoutInflater.from(parent.context).inflate(R.layout.view_cell, parent, false)
        // Devolvemos el Holder indicando cual es la vista que manejara
        return MainCellHolder(view)
    }

    // Conectamos el modelo de datos con nuestro ViewHolder
    override fun onBindViewHolder(holder: MainCellHolder, position: Int) {
        // Recupero cada cocktail por posicion y se lo asigno al holder
        val cocktail = cocktailsList[position]
        holder.cocktail = cocktail

        // Listener que escucha el tap en cada celda
        holder.view.setOnClickListener{
            selectedCellDelegate.onItemTap(cocktail)
        }
    }

    // Numero de elementos de la lista
    override fun getItemCount(): Int {
        return cocktailsList.size
    }


    /**
     * PUBLIC FUNCTIONS
     */

    // Metodo que repinta el modelo
    fun setChanges(cocktailsList: List<DrinksItem>) {
        this.cocktailsList = cocktailsList
        notifyDataSetChanged()
    }


    /**
     * VIEW HOLDER
     */

    // La inner class hereda de ViewHolder y asi rellena el contenedor de la celda
    inner class MainCellHolder(view: View) : RecyclerView.ViewHolder(view) {
        internal val view = view
        var cocktail: DrinksItem? = null
        set(value) {
            field = value
            setCellFields(field)
        }


        /**
         * PRIVATE FUNCTIONS
         */

        private fun setCellFields(cocktail: DrinksItem?) {
            // Al tag del elemento de la vista le indicamos cada elemento de celda
            view.tag = cocktail
            // Fijamos la informacion del cocktail
            view.cocktailText.text = cocktail?.strDrink

            view.favoriteImage.visibility = View.INVISIBLE
            if (cocktail?.bolFavorite == true)
                view.favoriteImage.visibility = View.VISIBLE

            // Fijamos la imagen recuperada en formato url con Glide
            Glide.with(context)
                .load(cocktail?.strDrinkThumb)
                .apply(RequestOptions().placeholder((R.drawable.ic_launcher_background)))
                .into(view.cocktailImage)
        }
    }
}