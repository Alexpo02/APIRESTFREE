package edu.pract5.apirestfree.data

import android.content.ContentValues.TAG
import android.util.Log
import androidx.room.Query
import edu.pract5.apirestfree.model.Alimento
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class Repository(db: AlimentosDataBase, val dataSource: RemoteDataSource) {

    private val localDataSource = LocalDataSource(db.alimentoDao())

    suspend fun fetchAlimentoApi(name: String): List<Alimento> {
        return dataSource.getAlimentoByName(name)
    }

    fun fetchAlimentoDB(name: String): Flow<List<Alimento>> {
        return localDataSource.getAlimentoByName(name)
    }

    suspend fun insertAlimentoDB(alimento: Alimento) {
        localDataSource.insertAlimento(alimento)
    }

    fun fetchAllAlimentosDB(): Flow<List<Alimento>> {
        return localDataSource.getAllAlimentos()
    }

    suspend fun deleteAlimento(alimento: Alimento) {
        localDataSource.deleteAlimento(alimento)
    }

    fun fetchAlimentosOrderByName(): Flow<List<Alimento>> {
        return localDataSource.getAlimentosOrderByName()
    }

    fun fetchAlimentos(query: String): Flow<List<Alimento>> {
        return flow {
            var resultDB = emptyList<Alimento>()
            try {
                // Se intenta recuperar la información de la base de datos.
                resultDB = localDataSource.getAlimentos()
                // Se intenta recuperar la información de la API.
                val resultAPI = dataSource.getAlimentoByName(query)
                // Se compara la información de la API y la de la base de datos.
                if (resultDB.containsAll(resultAPI)) {
                    // Se emite el resultado.

                    emit(resultDB)

                } else {
                    // Se inserta la información en la base de datos.

                    localDataSource.insertAlimentos(resultAPI)

                }
                // Se recupera la información de la base de datos actualizada.
                resultDB = localDataSource.getAlimentos()
            } catch (e: Exception) {
                // Se emite el error.
                Log.e(TAG, "fetchCities: ${e.message}")
            } finally {
                // Se emite el resultado, ya sea de la base de datos o de la API.
                // Una lista con datos o vacía.
                emit(resultDB)
            }
        }
    }
}
