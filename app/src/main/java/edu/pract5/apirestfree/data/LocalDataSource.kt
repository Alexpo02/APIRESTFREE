package edu.pract5.apirestfree.data

import android.util.Log
import edu.pract5.apirestfree.model.Alimento
import kotlinx.coroutines.flow.Flow

class LocalDataSource(val db: AlimentoDao) {

    suspend fun insertAlimento(alimento: Alimento) = db.insertAlimento(alimento)

    fun getAllAlimentos(): Flow<List<Alimento>> {
        Log.d("LocalDataSource", "Fetching all alimentos")
        return db.getAllAlimentos()
    }

    fun getAlimentoByName(name: String): Flow<List<Alimento>> = db.getAlimentoByName(name)

    fun getAlimentos(): List<Alimento> = db.getAlimentos()

    suspend fun insertAlimentos(alimentos: List<Alimento>) = db.insertAlimentos(alimentos)

    suspend fun deleteAlimento(alimento: Alimento) = db.deleteAlimento(alimento)

    fun getAlimentosOrderByName(): Flow<List<Alimento>> = db.getAlimentosOrderByName()
}