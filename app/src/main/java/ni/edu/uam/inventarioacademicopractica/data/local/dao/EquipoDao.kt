package ni.edu.uam.inventarioacademicopractica.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import ni.edu.uam.inventarioacademicopractica.data.local.entity.Equipo

@Dao
interface EquipoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(equipo: Equipo)

    @Update
    suspend fun update(equipo: Equipo)

    @Delete
    suspend fun delete(equipo: Equipo)

    @Query("SELECT * FROM equipos ORDER BY nombre ASC")
    fun getAllEquipos(): Flow<List<Equipo>>

    @Query("SELECT * FROM equipos WHERE disponible = 1 ORDER BY nombre ASC")
    fun getEquiposDisponibles(): Flow<List<Equipo>>

    @Query("SELECT * FROM equipos WHERE id = :id")
    suspend fun getEquipoById(id: Int): Equipo?

    @Query("SELECT * FROM equipos WHERE nombre LIKE '%' || :query || '%' OR numeroSerie LIKE '%' || :query || '%' ORDER BY nombre ASC")
    fun searchEquipos(query: String): Flow<List<Equipo>>
}
