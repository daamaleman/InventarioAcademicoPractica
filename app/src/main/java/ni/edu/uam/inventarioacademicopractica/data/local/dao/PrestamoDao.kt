package ni.edu.uam.inventarioacademicopractica.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import ni.edu.uam.inventarioacademicopractica.data.local.entity.Prestamo

@Dao
interface PrestamoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(prestamo: Prestamo)

    @Update
    suspend fun update(prestamo: Prestamo)

    @Query("SELECT * FROM prestamos ORDER BY id DESC")
    fun getAllPrestamos(): Flow<List<Prestamo>>
}
