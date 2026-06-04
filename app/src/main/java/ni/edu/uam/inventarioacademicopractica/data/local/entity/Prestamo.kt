package ni.edu.uam.inventarioacademicopractica.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "prestamos")
data class Prestamo(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val equipoId: Int,
    val solicitante: String,
    val fechaPrestamo: String,
    val fechaDevolucion: String?
)
