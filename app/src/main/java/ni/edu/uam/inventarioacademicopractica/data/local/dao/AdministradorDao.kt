package ni.edu.uam.inventarioacademicopractica.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import ni.edu.uam.inventarioacademicopractica.data.local.entity.Administrador

@Dao
interface AdministradorDao {
    @Query("SELECT * FROM administradores WHERE usuario = :usuario AND password = :password LIMIT 1")
    suspend fun login(usuario: String, password: String): Administrador?

    @Insert
    suspend fun insert(admin: Administrador)

    @Query("SELECT COUNT(*) FROM administradores")
    suspend fun countAdmins(): Int
}
