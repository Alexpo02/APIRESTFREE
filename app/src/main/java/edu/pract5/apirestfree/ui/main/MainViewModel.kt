package edu.pract5.apirestfree.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import edu.pract5.apirestfree.data.Repository
import edu.pract5.apirestfree.model.Alimento
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class MainViewModel(val repository: Repository) : ViewModel() {
    private var _currentAlimentos: Flow<List<Alimento>> = repository.fetchAllAlimentosDB()
    val currentAlimentos: Flow<List<Alimento>> get() = _currentAlimentos
    
    private var _currentAlimentosOrdered: Flow<List<Alimento>> = repository.fetchAlimentosOrderByName()
    val currentAlimentosOrdered: Flow<List<Alimento>> get() = _currentAlimentosOrdered

    suspend fun getAlimentoApi(query: String): List<Alimento> {
        return repository.fetchAlimentoApi(query)
    }

    fun deleteAlimento(alimento: Alimento) {
        viewModelScope.launch {
            repository.deleteAlimento(alimento)
        }
    }

    fun getAlimentoDB(query: String): Flow<List<Alimento>> {
        return repository.fetchAlimentoDB(query)
    }

    fun addAlimento(alimento: Alimento) {
        viewModelScope.launch {
            repository.insertAlimentoDB(alimento)
        }
    }
}

@Suppress("UNCHECKED_CAST")
class MainViewModelFactory(
    private val repository: Repository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainViewModel(repository) as T
    }
}
