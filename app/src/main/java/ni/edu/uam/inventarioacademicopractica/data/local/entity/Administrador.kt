package ni.edu.uam.inventarioacademicopractica.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "administradores")
data class Administrador(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val usuario: String,
    val password: String
)
