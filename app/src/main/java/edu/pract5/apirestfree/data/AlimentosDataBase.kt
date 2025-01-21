package edu.pract5.apirestfree.data

import androidx.room.Dao
import androidx.room.Database
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.RoomDatabase
import edu.pract5.apirestfree.model.Alimento
import kotlinx.coroutines.flow.Flow

@Database(entities = [Alimento::class], version = 2)
abstract class AlimentosDataBase : RoomDatabase() {
    abstract fun alimentoDao(): AlimentoDao

}

@Dao
interface AlimentoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAlimento(alimento: Alimento)

    @Query("SELECT * FROM alimentos")
    fun getAllAlimentos(): Flow<List<Alimento>>

    @Query("SELECT * FROM alimentos ORDER BY name ASC")
    fun getAlimentosOrderByName(): Flow<List<Alimento>>

    @Query("SELECT * FROM alimentos WHERE name LIKE :name")
    fun getAlimentoByName(name: String): Flow<List<Alimento>>

    @Delete
    suspend fun deleteAlimento(alimento: Alimento)
}