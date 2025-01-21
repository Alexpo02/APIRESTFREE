package edu.pract5.apirestfree

import android.app.Application
import androidx.room.Room
import edu.pract5.apirestfree.data.AlimentosDataBase

class RoomApplication : Application() {
    /**
     * Instancia de la base de datos Room para gestionar palabras.
     */
    lateinit var AlimentosDB: AlimentosDataBase
        private set

    /**
     * Método invocado cuando se crea la aplicación.
     * Inicializa la base de datos [AlimentosDataBase].
     */
    override fun onCreate() {
        super.onCreate()
        AlimentosDB = Room.databaseBuilder(
            this,
            AlimentosDataBase::class.java, "Alimentos-db"
        ).build()
    }
}