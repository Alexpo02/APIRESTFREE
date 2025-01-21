package edu.pract5.apirestfree.data

import android.util.Log
import edu.pract5.apirestfree.model.Alimento
import kotlinx.coroutines.flow.flow

class RemoteDataSource {
    private val api = Retrofit2Api.getRetrofit2Api()

    suspend fun getAlimentoByName(name: String): List<Alimento> {
        return api.getAlimentoByName(name)
    }
}