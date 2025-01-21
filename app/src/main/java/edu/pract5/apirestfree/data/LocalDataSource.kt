package edu.pract5.apirestfree.data

import android.util.Log
import edu.pract5.apirestfree.model.Alimento
import kotlinx.coroutines.flow.Flow

class LocalDataSource(val db: AlimentoDao) {

    suspend fun insertAlimento(alimento: Alimento) = db.insertAlimento(alimento)

    fun getAllAlimentos(): Flow<List<Alimento>> {
        return db.getAllAlimentos()
    }

    fun getAlimentoByName(name: String): Flow<List<Alimento>> = db.getAlimentoByName(name)

    suspend fun deleteAlimento(alimento: Alimento) = db.deleteAlimento(alimento)

    fun getAlimentosOrderByName(): Flow<List<Alimento>> = db.getAlimentosOrderByName()
}