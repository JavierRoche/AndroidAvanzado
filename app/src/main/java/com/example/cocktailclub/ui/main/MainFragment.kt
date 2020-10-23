package com.example.cocktailclub.ui.main

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cocktailclub.R
import com.example.cocktailclub.common.CustomViewModel
import com.example.cocktailclub.repository.network.CocktailDBApiService
import com.example.cocktailclub.repository.network.DrinksItem
import com.example.cocktailclub.ui.detail.DetailActivity
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.main_activity.*
import kotlinx.android.synthetic.main.main_fragment.*
import retrofit2.Response

class MainFragment : Fragment(), MainRecyclerAdapter.TapDelegate {
    // Para usar el adaptador y sus layouts
    private lateinit var adapter: MainRecyclerAdapter
    // Instancia del viewModel
    private val viewmodel: MainViewModel by lazy {
        val factory = CustomViewModel(activity!!.application)
        ViewModelProvider(this, factory).get(MainViewModel::class.java)
    }


    /**
     * STATIC INIT
     */

    companion object {
        const val TAG = "MainFragment"
        const val onFailureMessage = "Something bad happened. Refresh list."
        // Instancia estatica del fragmento
        fun newInstance(): MainFragment {
            val fragment = MainFragment()
            return fragment
        }
    }


    /**
     * LIFE CYCLE
     */

    // Ejecuta una vez se ha creado la instancia del fragmento
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    // Tanto la vista como el fragmento estan disponibles
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        // Pedimos al viewmodel la lista de cocktails
        viewmodel.getCocktailsList(object: CocktailDBApiService.CallbackResponse<List<DrinksItem>> {
            override fun onResponse(response: List<DrinksItem>) {
                // Comprobamos la BD local antes de informar la UI
                initUI(viewmodel.checkLocalDB(response))
            }

            override fun onFailure(t: Throwable, res: Response<*>?) {
                Snackbar.make(container, onFailureMessage, Snackbar.LENGTH_LONG).show()
            }
        })
    }


    /**
     * PUBLIC FUNCTIONS
     */

    fun changesOnCocktailList() {
        val emptyList: List<DrinksItem> = emptyList()
        // La actividad nos notifica de la vuelta de la vista de detalle
        val cocktailsList: List<DrinksItem> = viewmodel.checkLocalDB(emptyList)
        // Pasamos al adaptador la lista actualizada
        (cocktailsRecycler.adapter as MainRecyclerAdapter).setChanges(cocktailsList)
    }


    /**
     * PRIVATE FUNCTIONS
     */

    private fun initUI(cocktailsList: List<DrinksItem>) {
        activity?.applicationContext?.let { context ->
            // Construimos el adaptador con el contexto, el listener de eventos y la lista de cocktails
            adapter = MainRecyclerAdapter(context, this, cocktailsList)
            // Asignamos al recicler el adaptador creado y el layout
            cocktailsRecycler.adapter = adapter
            cocktailsRecycler.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        }
    }


    /**
     * USER INTERACTIONS PROTOCOL
     */

    override fun onItemTap(cocktail: DrinksItem?) {
        // Llamamos a la actividad de detalle pasandole el cocktail seleccionado como objeto serializable
        activity?.let {
            Intent(it, DetailActivity::class.java).apply {
                putExtra(TAG, cocktail)
                it.startActivityForResult(this, 100)
            }
        }
    }
}