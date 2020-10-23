package com.example.cocktailclub.repository.db

import androidx.room.*
import com.example.cocktailclub.repository.network.DrinksItem


/**
 * CONTIENE LOS ACCESOS A LA BD
 */

@Dao
abstract class DataAccessObject {
    @Query("SELECT * FROM cocktails_tb ORDER BY idDrink")
    abstract fun selectCocktails(): List<DrinksItem>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertCocktail(cocktail: DrinksItem)

    @Delete
    abstract fun deleteCocktail(cocktail: DrinksItem)
}