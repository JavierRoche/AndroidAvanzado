package com.example.cocktailclub.repository.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.cocktailclub.repository.network.DrinksItem

/**
 * MODELO DE DATOS
 */

// Se añaden las tablas en el array
@Database(entities = [DrinksItem::class], version = 1, exportSchema = false)

abstract class CocktailsRoom : RoomDatabase() {
    // Singleton para el acceso a la instancia de la BD
    companion object {
        // Instancia de la base de datos
        private var DBInstance: CocktailsRoom? = null

        fun getDBInstance(context: Context) : CocktailsRoom {
            if (DBInstance == null) {
                synchronized(CocktailsRoom::class) {
                    // Creacion de la instancia de la DB de Room
                    DBInstance = Room.databaseBuilder(context.applicationContext, CocktailsRoom::class.java, "FavoritesCocktailsDB")
                        // Sirve para destruir y recrear la BD ante un cambio de version
                        .fallbackToDestructiveMigration()
                        // Para hacer queries sobre la BD obliga a que sea en background y no se pueda usar el hilo principal
                        .allowMainThreadQueries()
                        .build()
                }
            }
            return DBInstance!!
        }
    }

    abstract fun dataAccessObject(): DataAccessObject
}