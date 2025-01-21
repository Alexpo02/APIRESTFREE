package edu.pract5.apirestfree.data

import android.util.Log
import edu.pract5.apirestfree.model.Alimento
import kotlinx.coroutines.flow.flow

class RemoteDataSource {
    private val api = Retrofit2Api.getRetrofit2Api()

    /*fun getAlimentoByName(name: String) = flow {
        try {
            val response = api.getAlimentoByName(name)
            Log.d("RemoteDataSource", "Respuesta de la API: $response")
            emit(response)
        } catch (e: Exception) {
            Log.e("RemoteDataSource", "Error al buscar alimento: ${e.message}")
            emit(emptyList()) // Devuelve una lista vacía en caso de error
        }
    }*/

    suspend fun getAlimentoByName(name: String): List<Alimento> {
        return api.getAlimentoByName(name)
    }
}