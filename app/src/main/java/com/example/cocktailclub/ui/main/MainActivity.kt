package com.example.cocktailclub.ui.main

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.cocktailclub.R

class MainActivity : AppCompatActivity() {
    /**
     * LIFE CYCLE
     */

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Indicamos cual es el fichero xml que manejara la actividad
        setContentView(R.layout.main_activity)

        // Comprobamos que sea la primera vez que se instancia la clase
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction().add(R.id.container, MainFragment.newInstance()).commitNow()
        }
    }

    // Escucha cuando se cierra la actividad que se ha llamado
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Podemos acceder a una funcion del fragmento a traves del supportFragmentManager
        if (requestCode == 100)
            (supportFragmentManager.findFragmentById(R.id.container) as MainFragment).changesOnCocktailList()
    }
}