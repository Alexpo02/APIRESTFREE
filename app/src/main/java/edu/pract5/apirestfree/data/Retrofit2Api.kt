package edu.pract5.apirestfree.data

import edu.pract5.apirestfree.model.Alimento
import retrofit2.Retrofit
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query
import edu.pract5.apirestfree.BuildConfig

class Retrofit2Api {
    companion object {
        const val BASE_URL = "https://api.api-ninjas.com/v1/"
        fun getRetrofit2Api(): AlimentosApiInterface {
            return Retrofit.Builder().baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create()).build()
                .create(AlimentosApiInterface::class.java)
        }
    }
}

interface AlimentosApiInterface {
    // Define el endpoint y solo pasa el parámetro necesario

    @Headers("X-Api-Key: ${BuildConfig.API_KEY}")
    @GET("nutrition")
    suspend fun getAlimentoByName(
        @Query("query") name: String // El nombre del alimento que deseas buscar
    ): List<Alimento> // La respuesta es una lista de alimentos
}
