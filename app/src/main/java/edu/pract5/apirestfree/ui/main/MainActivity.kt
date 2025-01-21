package edu.pract5.apirestfree.ui.main

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.widget.SearchView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.google.android.material.snackbar.Snackbar
import edu.pract5.apirestfree.R
import edu.pract5.apirestfree.RoomApplication
import edu.pract5.apirestfree.data.LocalDataSource
import edu.pract5.apirestfree.data.RemoteDataSource
import edu.pract5.apirestfree.data.Repository
import edu.pract5.apirestfree.databinding.ActivityMainBinding
import edu.pract5.apirestfree.model.Alimento
import edu.pract5.apirestfree.ui.alimento.DetailAlimentoActivity
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private var isOrdered = false // Indica si los datos están ordenados
    private var query: String? = null // Consulta actual de búsqueda

    private val adapter = AlimentosAdapter(
        onAlimentoClick = { alimento ->
            Log.d("MainActivity", "Alimento seleccionado: $alimento")
            DetailAlimentoActivity.navigate(this@MainActivity, alimento)
        },
        onAlimentoDeleteClick = { alimento, _ ->
            vm.deleteAlimento(alimento)
            Snackbar.make(
                binding.root,
                getString(R.string.txt_alimentoDeleted, alimento.name),
                Snackbar.LENGTH_LONG
            ).setAction(R.string.txt_undo) {
                lifecycleScope.launch {
                    vm.addAlimento(alimento) // Deshacer eliminación
                    updateAlimentosList()
                }
            }.show()
            updateAlimentosList()
        }
    )

    private val vm: MainViewModel by viewModels {
        val db = (application as RoomApplication).AlimentosDB
        val ds = RemoteDataSource()
        MainViewModelFactory(Repository(db, ds))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.recyclerView.adapter = adapter
        binding.materialToolbar.inflateMenu(R.menu.filtro)
        binding.materialToolbar.inflateMenu(R.menu.acerca_de)

        binding.materialToolbar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.itemQuitarFiltro -> {
                    isOrdered = false
                    updateAlimentosList()
                    true
                }
                R.id.itemOrdenar -> {
                    isOrdered = true
                    updateAlimentosList()
                    true
                }
                R.id.itemAcercaDe -> {
                    showAboutDialog()
                    true
                }
                else -> false
            }
        }

        // Cargar datos al iniciar
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                updateAlimentosList()
            }
        }
    }

    override fun onStart() {
        super.onStart()
        // Aplica el filtro y la búsqueda actual al reanudar
        updateAlimentosList()

        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                this@MainActivity.query = query
                lifecycleScope.launch {
                    if (!query.isNullOrEmpty()) {
                        if (vm.getAlimentoDB(query).first().isEmpty()) {
                            getAlimentos(query) // Buscar y guardar en la API
                        } else {
                            getAlimentoFromDB(query) // Mostrar desde la base de datos
                        }
                    }
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                this@MainActivity.query = newText
                lifecycleScope.launch {
                    if (newText.isNullOrEmpty()) {
                        updateAlimentosList() // Muestra todo si no hay consulta
                    }
                }
                return true
            }
        })
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putBoolean("isOrdered", isOrdered)
        outState.putString("query", query)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        isOrdered = savedInstanceState.getBoolean("isOrdered", false)
        query = savedInstanceState.getString("query")
        updateAlimentosList()
    }

    // Actualiza la lista de alimentos según el estado actual
    private fun updateAlimentosList() {
        lifecycleScope.launch {
            if (query.isNullOrEmpty()) {
                getAlimentosFromDB()
            } else {
                getAlimentoFromDB(query!!)
            }
        }
    }

    private suspend fun getAlimentos(query: String) {
        try {
            val alimentosApi = vm.getAlimentoApi(query).first()
            vm.addAlimento(alimentosApi)
            getAlimentoFromDB(query)
        } catch (e: Exception) {
            Log.e(TAG, "Error al buscar alimento: ${e.message}")
        }
    }

    private suspend fun getAlimentoFromDB(query: String) {
        try {
            vm.getAlimentoDB(query).collect { alimentosDb ->
                adapter.submitList(if (isOrdered) alimentosDb.sortedBy { it.name } else alimentosDb)
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error al buscar en la base de datos: ${e.message}")
        }
    }

    private suspend fun getAlimentosFromDB() {
        try {
            val alimentos = if (isOrdered) vm.currentAlimentosOrdered.first() else vm.currentAlimentos.first()
            adapter.submitList(alimentos)
        } catch (e: Exception) {
            Log.e(TAG, "Error al obtener alimentos de la base de datos: ${e.message}")
        }
    }

    private fun showAboutDialog() {
        val dialog = androidx.appcompat.app.AlertDialog.Builder(this)
            .setTitle("Acerca de")
            .setMessage(
                """
            Nombre: Alex
            Apellidos: Expósito Picó
            Curso: Desarrollo de aplicaciones multiplatafomra
            Grupo: 2A
            Año académico: 2024-2025
            """.trimIndent()
            )
            .setPositiveButton("Cerrar") { dialog, _ ->
                dialog.dismiss() // Cerrar el diálogo
            }
            .create()
        dialog.show()
    }
}
