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
}
